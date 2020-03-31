/*
 *
 * Copyright (C) HeonJik, KIM
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */

package kr.xdbc.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.ParameterMetaData;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Date;
import java.util.Calendar;
import java.sql.PreparedStatement;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Array;
import java.sql.Ref;
import java.sql.Blob;
import java.sql.Clob;
import java.math.BigDecimal;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Handler;
import kr.xdbc.trace.ConnectionManager;
import kr.xdbc.util.StringUtil;

/**
 * PreparedStatement Wrapper

 * @author HeonJik, KIM
 * @version 0.2
 * @since 0.1
 */
public final class PreparedStatementImpl implements PreparedStatement {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private int index;
	private Connection con;
	private PreparedStatement pstmt;
	private PreparedStatement[] pstmts;
/**
 * SQL Query
 */
	private String sql = new String();
/**
 * 파라미터 Array
 */
	ArrayList al;
	public PreparedStatementImpl(Connection con, PreparedStatement pstmt, String sql) throws java.sql.SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.sql = sql;
		this.pstmt = pstmt;
		this.pstmts = null;
		this.al = new ArrayList();
		/*
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		logger.addHandler(handler);
		logger.setLevel(Level.ALL);

		Handler[] handlers = logger.getHandlers();
		for(int i = 0; i < handlers.length; i++) {
			handlers[i].setLevel(Level.ALL);
			if(i > 0) {
				logger.removeHandler(handlers[i]);
			}
		}
		*/
	}
	public PreparedStatementImpl(Connection con, PreparedStatement[] pstmts, String sql) throws java.sql.SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.sql = sql;
		this.pstmts = pstmts;
		this.al = new ArrayList();
		/*
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		logger.addHandler(handler);
		logger.setLevel(Level.ALL);

		Handler[] handlers = logger.getHandlers();
		for(int i = 0; i < handlers.length; i++) {
			handlers[i].setLevel(Level.ALL);
			if(i > 0) {
				logger.removeHandler(handlers[i]);
			}
		}
		*/
	}
/**
 * SQL을 반환한다.
 */
	public String getQuery() {
		return sql;
	}
/**
 * BIND 변수가 치환된 SQL을 반환한다.
 */
	public String getQueryString() {
		StringBuffer sb = new StringBuffer();
		char[] array = sql.toCharArray();
		char prev = 0;
		boolean quote = false;
		boolean comment = false;
		int index = 1;
		char p = 0;
		char c = 0;
		for(int i = 0; i < array.length; i++) {
			switch(array[i]) {
				case '?':
					prev = array[i];
					if(!quote && !comment) {
						if(index <= al.size()) {
							sb.append(al.get(index));
						} else {
							sb.append(array[i]);
						}
						index++;
					} else {
						sb.append(array[i]);
					}
					break;
				case '\'':
					if(!comment && !quote) {
						p = array[i];
						quote = !quote;
					} else if(!comment && p == array[i]){
						if(i <= array.length - 2 && array[i + 1] == p) {
							sb.append(array[i]);
							i++;
						} else {
							quote = !quote;
						}
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				case '"':
					if(!comment && !quote) {
						p = array[i];
						quote = !quote;
					} else if(!comment && prev != '\\' && p == array[i]){
						quote = !quote;
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				case '/':
					if(comment && prev == '*' && c == '*') {
						comment = !comment;
					} else if(!quote && !comment && prev == '/') {
						c = array[i];
						comment = !comment;
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				case '*':
					if(!quote && !comment && prev == '/') {
						c = array[i];
						comment = !comment;
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				case '\n':
					if(comment && c == '/') {
						comment = !comment;
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				default :
					sb.append(array[i]);
					prev = array[i];
			}
		}
		return sb.toString();
	}
 /*
	public String getQueryString() {
		Object[] sqls = StringUtil.explode(sql.toString(), "?");
		
		Object[] variables = al.toArray();
		StringBuffer sb = new StringBuffer();
		if(sqls != null) {
			for(int i = 0; i < sqls.length; i ++) {
				sb.append(sqls[i] + "" + StringUtil.gets(variables, i + 1) + "");
			}
		}
		return sb.toString();
	}
	*/
	
/**
 *변수를 Setting 한다.
 */
	public void setVariable(int position, Object obj) {
		while (position >= al.size()) {
			al.add(null);
		}
		if(obj == null) {
			al.set(position, "null");
		} else if(obj instanceof Integer) {
			al.set(position, obj);
		} else {
			al.set(position, "'" + obj.toString() + "'");
		}
	}
	public void addBatch() throws java.sql.SQLException {
		try {
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					this.pstmts[i].addBatch();
				}
			} else {
				pstmt.addBatch();
			}
			logger.fine(this.getQueryString());
		} catch (SQLException e) {
			logger.severe(this.getQueryString());
			throw e;
		}
	}
	public void clearParameters()throws java.sql.SQLException {
		al.clear();
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].clearParameters();
			}
		} else {
			pstmt.clearParameters();
		}
	}
	public boolean execute(String sql) throws java.sql.SQLException {
		try {
			boolean issucess = false;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					issucess = this.pstmts[i].execute(sql);
				}
			} else {
				issucess = pstmt.execute(sql);
			}
			logger.fine(sql);
			return issucess;
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public ResultSet executeQuery() throws java.sql.SQLException {
		try {
			if(pstmts != null) {
				ResultSet rs = new ResultSetImpl(this, pstmts[0].executeQuery());
				logger.fine(this.getQueryString());
				return rs;
			} else {
				ResultSet rs = new ResultSetImpl(this, pstmt.executeQuery());
				logger.fine(this.getQueryString());
				return rs;
			}
		} catch (SQLException e) {
			logger.severe(this.getQueryString());
			throw e;
		}
	}
	public int executeUpdate() throws java.sql.SQLException {
		try {
			int result = 0;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate();
				}
			} else {
				result = pstmt.executeUpdate();
			}
			logger.fine(this.getQueryString());
			return result;
		} catch (SQLException e) {
			logger.severe(this.getQueryString());
			throw e;
		}
	}
	public ResultSetMetaData getMetaData() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getMetaData();
		} else  {
			return pstmt.getMetaData();
		}
	}
	public ParameterMetaData getParameterMetaData() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getParameterMetaData();
		} else  {
			return pstmt.getParameterMetaData();
		}
	}
	public void setArray(int i,Array  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int y = 0; y < pstmts.length; y++) {
				this.pstmts[y].setArray(i, x);
			}
		} else {
			pstmt.setArray(i, x);
		}
		setVariable(i, x);
	}
	public void setAsciiStream(int parameterIndex,InputStream  x,int length) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setAsciiStream(parameterIndex, x, length);
			}
		} else {
			pstmt.setAsciiStream(parameterIndex, x, length);
		}
	}
	public void setBigDecimal(int parameterIndex,BigDecimal  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setBigDecimal(parameterIndex, x);
			}
		} else {
			pstmt.setBigDecimal(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setBinaryStream(int parameterIndex,InputStream  x,int length) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setBinaryStream(parameterIndex, x, length);
			}
		} else {
			pstmt.setBinaryStream(parameterIndex, x, length);
		}
	}
	public void setBlob(int i,Blob  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int y = 0; y < pstmts.length; y++) {
				this.pstmts[y].setBlob(i, x);
			}
		} else {
			pstmt.setBlob(i, x);
		}
		setVariable(i, x);
	}
	public void setBoolean(int parameterIndex,boolean x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setBoolean(parameterIndex, x);
			}
		} else {
			pstmt.setBoolean(parameterIndex, x);
		}
		setVariable(parameterIndex, new Boolean(x));
	}
	public void setByte(int parameterIndex,byte x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setByte(parameterIndex, x);
			}
		} else {
			pstmt.setByte(parameterIndex, x);
		}
		setVariable(parameterIndex, new Byte(x));
	}
	public void setBytes(int parameterIndex,byte[] x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setBytes(parameterIndex, x);
			}
		} else {
			pstmt.setBytes(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setCharacterStream(int parameterIndex,Reader  reader,int length) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setCharacterStream(parameterIndex, reader, length);
			}
		} else {
			pstmt.setCharacterStream(parameterIndex, reader, length);
		}
	}
	public void setClob(int i,Clob  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int y = 0; y < pstmts.length; y++) {
				this.pstmts[y].setClob(i, x);
			}
		} else {
			pstmt.setClob(i, x);
		}
		setVariable(i, x);
	}
	public void setDate(int parameterIndex,Date  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setDate(parameterIndex, x);
			}
		} else {
			pstmt.setDate(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setDate(int parameterIndex,Date  x,Calendar  cal) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setDate(parameterIndex, x, cal);
			}
		} else {
			pstmt.setDate(parameterIndex, x, cal);
		}
		
	}
	public void setDouble(int parameterIndex,double x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setDouble(parameterIndex, x);
			}
		} else {
			pstmt.setDouble(parameterIndex, x);
		}
		setVariable(parameterIndex, new Double(x));
	}
	public void setFloat(int parameterIndex,float x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setFloat(parameterIndex, x);
			}
		} else {
			pstmt.setFloat(parameterIndex, x);
		}
		setVariable(parameterIndex, new Float(x));
	}
	public void setInt(int parameterIndex,int x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setInt(parameterIndex, x);
			}
		} else {
			pstmt.setInt(parameterIndex, x);
		}
		setVariable(parameterIndex, new Integer(x));
	}
	public void setLong(int parameterIndex,long x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setLong(parameterIndex, x);
			}
		} else {
			pstmt.setLong(parameterIndex, x);
		}
		setVariable(parameterIndex, new Long(x));
	}
	public void setNull(int parameterIndex,int sqlType) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setNull(parameterIndex, sqlType);
			}
		} else {
			pstmt.setNull(parameterIndex, sqlType);
		}
		setVariable(parameterIndex, null);
	}
	public void setNull(int paramIndex,int sqlType, String  typeName) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setNull(paramIndex, sqlType, typeName);
			}
		} else {
			pstmt.setNull(paramIndex, sqlType, typeName);
		}
		setVariable(paramIndex, null);
	}
	public void setObject(int parameterIndex,Object  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setObject(parameterIndex, x);
			}
		} else {
			pstmt.setObject(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setObject(int parameterIndex,Object  x,int targetSqlType) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setObject(parameterIndex, x, targetSqlType);
			}
		} else {
			pstmt.setObject(parameterIndex, x, targetSqlType);
		}
		setVariable(parameterIndex, x);
	}
	public void setObject(int parameterIndex, Object  x, int targetSqlType, int scale) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setObject(parameterIndex, x, targetSqlType, scale);
			}
		} else {
			pstmt.setObject(parameterIndex, x, targetSqlType, scale);
		}
		setVariable(parameterIndex, x);
	}
	public void setRef(int i,Ref  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int y = 0; y < pstmts.length; y++) {
				this.pstmts[y].setRef(i, x);
			}
		} else {
			pstmt.setRef(i, x);
		}
		setVariable(i, x);
	}
	public void setShort(int parameterIndex,short x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setShort(parameterIndex, x);
			}
		} else {
		pstmt.setShort(parameterIndex, x);
		}
		setVariable(parameterIndex, new Short(x));
	}
	public void setString(int parameterIndex, String x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setString(parameterIndex, x);
			}
		} else {
			pstmt.setString(parameterIndex, x);
		}

		setVariable(parameterIndex, x);
	}
	public void setTime(int parameterIndex,Time  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setTime(parameterIndex, x);
			}
		} else {
			pstmt.setTime(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setTime(int parameterIndex,Time  x,Calendar  cal) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setTime(parameterIndex, x, cal);
			}
		} else {
			pstmt.setTime(parameterIndex, x, cal);
		}
		setVariable(parameterIndex, x);
	}
	public void setTimestamp(int parameterIndex,Timestamp  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setTimestamp(parameterIndex, x);
			}
		} else {
		pstmt.setTimestamp(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setTimestamp(int parameterIndex,Timestamp  x,Calendar  cal) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setTimestamp(parameterIndex, x, cal);
			}
		} else {
			pstmt.setTimestamp(parameterIndex, x, cal);
		}
		setVariable(parameterIndex, x);
	}
/**
 * @deprecated      
 */
	public void setUnicodeStream(int parameterIndex, InputStream  x,int length) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setUnicodeStream(parameterIndex, x, length);
			}
		} else {
			pstmt.setUnicodeStream(parameterIndex, x, length);
		}
	}
	public void setURL(int parameterIndex,URL  x) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setURL(parameterIndex, x);
			}
		} else {
			pstmt.setURL(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void addBatch(String  sql) throws java.sql.SQLException {
		try {
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					this.pstmts[i].addBatch(sql);
				}
			} else {
				pstmt.addBatch(sql);
			}
			logger.fine(sql);
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public void cancel() throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].cancel();
			}
		} else {
		pstmt.cancel();
		}
	}
	public void clearBatch() throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].clearBatch();
			}
		} else {
		pstmt.clearBatch();
		}
	}
	public void clearWarnings() throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].clearWarnings();
			}
		} else {
			pstmt.clearWarnings();
		}
	}
	public void close() throws java.sql.SQLException {
		ConnectionManager.getInstance().clear(this.index);
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].close();
			}
		} else {
			pstmt.close();
		}
	}
	public boolean execute() throws java.sql.SQLException {
		try {
			boolean issucess = false;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					issucess = this.pstmts[i].execute();
				}
			} else {
				issucess = pstmt.execute();
			}
			logger.fine(this.getQueryString());
			return issucess;
		} catch (SQLException e) {
			logger.severe(this.getQueryString());
			throw e;
		}
	}
	public boolean execute(String  sql,int autoGeneratedKeys) throws java.sql.SQLException {
		try {
			boolean issucess = false;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					issucess = this.pstmts[i].execute(sql, autoGeneratedKeys);
				}
			} else {
				issucess = issucess = pstmt.execute(sql, autoGeneratedKeys);
			}
			logger.fine(sql);
			return issucess;
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public boolean execute(String  sql, int[] columnIndexes) throws java.sql.SQLException {
		try {
			boolean issucess = false;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					issucess = this.pstmts[i].execute(sql, columnIndexes);
				}
			} else {
				issucess = pstmt.execute(sql, columnIndexes);
			}
			logger.fine(sql);
			return issucess;
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public boolean execute(String  sql,String [] columnNames) throws java.sql.SQLException {
		try {
			boolean result = false;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					result = this.pstmts[i].execute(sql, columnNames);
				}
			} else {
				result = pstmt.execute(sql, columnNames);
			}
			logger.fine(sql);
			return result;
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public int[] executeBatch() throws java.sql.SQLException {
		if(pstmts != null) {
			int[] r = new int[0];
			for(int i = 0; i < pstmts.length; i++) {
				r = this.pstmts[i].executeBatch();
			}
			return r;
		} else {
			return pstmt.executeBatch();
		}
	}
	public ResultSet executeQuery(String  sql) throws java.sql.SQLException {
		try {
			if(pstmts != null) {
				ResultSet rs = new ResultSetImpl(this, pstmts[0].executeQuery(sql));
				logger.fine(sql);
				return rs;
			} else  {
				ResultSet rs = new ResultSetImpl(this, pstmt.executeQuery(sql));
				logger.fine(sql);
				return rs;
			}
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public int executeUpdate(String  sql) throws java.sql.SQLException {
		try {
			int result = 0;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate(sql);
				}
			} else {
				result = pstmt.executeUpdate(sql);
			}
			logger.fine(sql);
			return result;
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public int executeUpdate(String  sql,int autoGeneratedKeys) throws java.sql.SQLException {
		try {
			int result = 0;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate(sql, autoGeneratedKeys);
				}
			} else {
				result = pstmt.executeUpdate(sql, autoGeneratedKeys);
			}
			logger.fine(sql);
			return result;
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public int executeUpdate(String  sql,int[] columnIndexes) throws java.sql.SQLException {
		try {
			int result = 0;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate(sql, columnIndexes);
				}
			} else {
				result = pstmt.executeUpdate(sql, columnIndexes);
			}
			logger.fine(sql);
			return result;
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public int executeUpdate(String  sql,String [] columnNames) throws java.sql.SQLException {
		try {
			int result = 0;
			if(pstmts != null) {
				for(int i = 0; i < pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate(sql, columnNames);
				}
			} else {
				result = pstmt.executeUpdate(sql, columnNames);
			}
			logger.fine(sql);
			return result;
		} catch (SQLException e) {
			logger.severe(sql);
			throw e;
		}
	}
	public Connection getConnection() throws java.sql.SQLException {
		return this.con;
	}
	public int getFetchDirection() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getFetchDirection();
		} else  {
		return pstmt.getFetchDirection();
		}
	}
	public int getFetchSize() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getFetchSize();
		} else  {
		return pstmt.getFetchSize();
		}
	}
	public ResultSet getGeneratedKeys() throws java.sql.SQLException {
		if(pstmts != null) {
			return new ResultSetImpl(this, pstmts[0].getGeneratedKeys());
		} else  {
		return new ResultSetImpl(this, pstmt.getGeneratedKeys());
		}
	}
	public int getMaxFieldSize() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getMaxFieldSize();
		} else  {
		return pstmt.getMaxFieldSize();
		}
	}
	public int getMaxRows() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getMaxRows();
		} else  {
		return pstmt.getMaxRows();
		}
	}
	public boolean getMoreResults() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getMoreResults();
		} else  {
		return pstmt.getMoreResults();
		}
	}
	public boolean getMoreResults(int current) throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getMoreResults(current);
		} else  {
		return pstmt.getMoreResults(current);
		}
	}
	public int getQueryTimeout() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getQueryTimeout();
		} else  {
		return pstmt.getQueryTimeout();
		}
	}
	public ResultSet getResultSet() throws java.sql.SQLException {
		if(pstmts != null) {
			return new ResultSetImpl(this, pstmts[0].getResultSet());
		} else  {
		return new ResultSetImpl(this, pstmt.getResultSet());
		}
	}
	public int getResultSetConcurrency() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getResultSetConcurrency();
		} else  {
		return pstmt.getResultSetConcurrency();
		}
	}
	public int getResultSetHoldability() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getResultSetHoldability();
		} else  {
		return pstmt.getResultSetHoldability();
		}
	}
	public int getResultSetType() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getResultSetType();
		} else  {
		return pstmt.getResultSetType();
		}
	}
	public int getUpdateCount() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getUpdateCount();
		} else  {
		return pstmt.getUpdateCount();
		}
	}
	public SQLWarning getWarnings() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].getWarnings();
		} else  {
		return pstmt.getWarnings();
		}
	}
	public void setCursorName(String  name) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setCursorName(name);
			}
		} else {
		pstmt.setCursorName(name);
		}
	}
	public void setEscapeProcessing(boolean enable) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setEscapeProcessing(enable);
			}
		} else {
		pstmt.setEscapeProcessing(enable);
		}
	}
	public void setFetchDirection(int direction) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setFetchDirection(direction);
			}
		} else {
		pstmt.setFetchDirection(direction);
		}
	}
	public void setFetchSize(int rows) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setFetchSize(rows);
			}
		} else {
		pstmt.setFetchSize(rows);
		}
	}
	public void setMaxFieldSize(int max) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setMaxFieldSize(max);
			}
		} else {
		pstmt.setMaxFieldSize(max);
		}
	}
	public void setMaxRows(int max) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setMaxRows(max);
			}
		} else {
		pstmt.setMaxRows(max);
		}
	}
	public void setQueryTimeout(int seconds) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setQueryTimeout(seconds);
			}
		} else {
		pstmt.setQueryTimeout(seconds);
		}
	}
	public void setAsciiStream(int arg0, InputStream arg1, long arg2) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setAsciiStream(arg0, arg1, arg2);
			}
		} else {
		this.pstmt.setAsciiStream(arg0, arg1, arg2);
		}
	}
	public void setAsciiStream(int arg0, InputStream arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setAsciiStream(arg0, arg1);
			}
		} else {
		this.pstmt.setAsciiStream(arg0, arg1);
		}
	}
	public void setBinaryStream(int arg0, InputStream arg1, long arg2) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setBinaryStream(arg0, arg1, arg2);
			}
		} else {
			this.pstmt.setBinaryStream(arg0, arg1, arg2);
		}
	}
	public void setBinaryStream(int arg0, InputStream arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setBinaryStream(arg0, arg1);
			}
		} else {
			this.pstmt.setBinaryStream(arg0, arg1);
		}
	}
	public void setBlob(int arg0, InputStream arg1, long arg2) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setBlob(arg0, arg1, arg2);
			}
		} else {
			this.pstmt.setBlob(arg0, arg1, arg2);
		}
	}
	public void setBlob(int arg0, InputStream arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setBlob(arg0, arg1);
			}
		} else {
			this.pstmt.setBlob(arg0, arg1);
		}
	}
	public void setCharacterStream(int arg0, Reader arg1, long arg2) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setCharacterStream(arg0, arg1, arg2);
			}
		} else {
			this.pstmt.setCharacterStream(arg0, arg1, arg2);
		}
	}
	public void setCharacterStream(int arg0, Reader arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setCharacterStream(arg0, arg1);
			}
		} else {
			this.pstmt.setCharacterStream(arg0, arg1);
		}
	}
	public void setClob(int arg0, Reader arg1, long arg2) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setClob(arg0, arg1, arg2);
			}
		} else {
			this.pstmt.setClob(arg0, arg1, arg2);
		}
	}
	public void setClob(int arg0, Reader arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setClob(arg0, arg1);
			}
		} else {
			this.pstmt.setClob(arg0, arg1);
		}
	}
	public void setNCharacterStream(int arg0, Reader arg1, long arg2) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setNCharacterStream(arg0, arg1, arg2);
			}
		} else {
			this.pstmt.setNCharacterStream(arg0, arg1, arg2);
		}

	}
	public void setNCharacterStream(int arg0, Reader arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setNCharacterStream(arg0, arg1);
			}
		} else {
			this.pstmt.setNCharacterStream(arg0, arg1);
		}
	}
	public void setNClob(int arg0, java.sql.NClob arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setNClob(arg0, arg1);
			}
		} else {
			this.pstmt.setNClob(arg0, arg1);
		}
	}
	public void setNClob(int arg0, Reader arg1, long arg2) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setNClob(arg0, arg1, arg2);
			}
		} else {
			this.pstmt.setNClob(arg0, arg1, arg2);
		}
	}
	public void setNClob(int arg0, Reader arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setNClob(arg0, arg1);
			}
		} else {
			this.pstmt.setNClob(arg0, arg1);
		}
	}
	public void setNString(int arg0, String arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setNString(arg0, arg1);
			}
		} else {
			this.pstmt.setNString(arg0, arg1);
		}
	}
	public void setRowId(int arg0, java.sql.RowId arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setRowId(arg0, arg1);
			}
		} else {
			this.pstmt.setRowId(arg0, arg1);
		}
	}
	public void setSQLXML(int arg0, java.sql.SQLXML arg1) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setSQLXML(arg0, arg1);
			}
		} else {
			this.pstmt.setSQLXML(arg0, arg1);
		}
	}
	
	public void closeOnCompletion() throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].closeOnCompletion();
			}
		} else {
			this.pstmt.closeOnCompletion();
		}
	}
	public boolean isClosed() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].isClosed();
		} else  {
			return this.pstmt.isClosed();
		}
	}
	public boolean isCloseOnCompletion() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].isCloseOnCompletion();
		} else  {
			return this.pstmt.isCloseOnCompletion();
		}
	}
	public boolean isPoolable() throws java.sql.SQLException {
		if(pstmts != null) {
			return pstmts[0].isPoolable();
		} else  {
			return this.pstmt.isPoolable();
		}
	}
	public void setPoolable(boolean arg0) throws java.sql.SQLException {
		if(pstmts != null) {
			for(int i = 0; i < pstmts.length; i++) {
				this.pstmts[i].setPoolable(arg0);
			}
		} else {
			this.pstmt.setPoolable(arg0);
		}
	}
	
	public java.lang.Object unwrap(java.lang.Class arg0) throws java.sql.SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				if(i == this.pstmts.length - 1) {
					return pstmts[i].unwrap(arg0);
				} else {
					pstmts[i].unwrap(arg0);
				}
			}
			return null;
		} else {
			return this.pstmt.unwrap(arg0);
		}
	}
	public boolean isWrapperFor(java.lang.Class arg0) throws java.sql.SQLException {
		if(pstmts != null) {
			return this.pstmts[0].isWrapperFor(arg0);
		} else {
			return this.pstmt.isWrapperFor(arg0);
		}
	}
}
