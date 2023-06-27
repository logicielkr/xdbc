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

package kr.xdbc.java8.sql;

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
import java.sql.SQLXML;
import java.sql.SQLType;
import java.sql.RowId;
import java.sql.NClob;
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
 * API reference 로부터 자동으로 생성한 코드가 아닌 경우 javadoc 을 남긴다.

 * @author HeonJik, KIM
 * @version 0.3
 * @since 0.3
 */
public final class PreparedStatementImpl implements PreparedStatement {
/**
 * Logger
 */
	private Logger logger = Logger.getLogger(this.getClass().getName());
/**
 * ConnectionManager에서 관리하는 일련번호
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	private int index;
/**
 * 내부적으로 관리하는 객체
 */
	private Connection con;
/**
 * 내부적으로 관리하는 객체
 */
	private PreparedStatement pstmt;
/**
 * 내부적으로 관리하는 객체 (Connection 이 여러개인 경우)
 */
	private PreparedStatement[] pstmts;
/**
 * SQL Query
 */
	private String sql = new String();
/**
 * 파라미터 Array
 */
	private ArrayList _al;
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	public PreparedStatementImpl(Connection con, PreparedStatement pstmt, String sql) throws java.sql.SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.sql = sql;
		this.pstmt = pstmt;
		this.pstmts = null;
		this._al = new ArrayList();
	}
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	public PreparedStatementImpl(Connection con, PreparedStatement[] pstmts, String sql) throws java.sql.SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.sql = sql;
		this.pstmts = pstmts;
		this._al = new ArrayList();
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
		return StringUtil.getQueryString(sql, this._al, null);
	}
/**
 *변수를 Setting 한다.
 */
	public void setVariable(int position, Object obj) {
		while (position >= this._al.size()) {
			this._al.add(null);
		}
		if(obj == null) {
			this._al.set(position, "null");
		} else if(obj instanceof Integer) {
			this._al.set(position, obj);
		} else {
			this._al.set(position, "'" + obj.toString() + "'");
		}
	}
/**
 * Statement 가 여러 개인 경우 첫 번째 것을 실행하고 반환받은 ResultSet 을 ResultSetImpl 로 감싸서 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public ResultSet executeQuery() throws SQLException {
		try {
			if(this.pstmts != null) {
				ResultSet rs = new ResultSetImpl(this, this.pstmts[0].executeQuery(), true);
				if(logger.isLoggable(Level.FINER)) { logger.finer(this.getQueryString()); }
				return rs;
			} else {
				ResultSet rs = new ResultSetImpl(this, this.pstmt.executeQuery(), false);
				if(logger.isLoggable(Level.FINER)) { logger.finer(this.getQueryString()); }
				return rs;
			}
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(this.getQueryString()); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate() throws SQLException {
		try {
			int result = 0;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate();
				}
			} else {
				result = this.pstmt.executeUpdate();
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(this.getQueryString()); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(this.getQueryString()); }
			throw e;
		}
	}
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setNull(parameterIndex, sqlType);
			}
		} else {
			this.pstmt.setNull(parameterIndex, sqlType);
		}
		setVariable(parameterIndex, null);
	}
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setBoolean(parameterIndex, x);
			}
		} else {
			this.pstmt.setBoolean(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setByte(int parameterIndex, byte x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setByte(parameterIndex, x);
			}
		} else {
			this.pstmt.setByte(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setShort(int parameterIndex, short x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setShort(parameterIndex, x);
			}
		} else {
			this.pstmt.setShort(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setInt(int parameterIndex, int x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setInt(parameterIndex, x);
			}
		} else {
			this.pstmt.setInt(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setLong(int parameterIndex, long x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setLong(parameterIndex, x);
			}
		} else {
			this.pstmt.setLong(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setFloat(int parameterIndex, float x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setFloat(parameterIndex, x);
			}
		} else {
			this.pstmt.setFloat(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setDouble(int parameterIndex, double x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setDouble(parameterIndex, x);
			}
		} else {
			this.pstmt.setDouble(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setBigDecimal(parameterIndex, x);
			}
		} else {
			this.pstmt.setBigDecimal(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setString(int parameterIndex, String x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setString(parameterIndex, x);
			}
		} else {
			this.pstmt.setString(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setBytes(parameterIndex, x);
			}
		} else {
			this.pstmt.setBytes(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setDate(int parameterIndex, Date x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setDate(parameterIndex, x);
			}
		} else {
			this.pstmt.setDate(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setTime(int parameterIndex, Time x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setTime(parameterIndex, x);
			}
		} else {
			this.pstmt.setTime(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setTimestamp(parameterIndex, x);
			}
		} else {
			this.pstmt.setTimestamp(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setAsciiStream(parameterIndex, x, length);
			}
		} else {
			this.pstmt.setAsciiStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
@Deprecated
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setUnicodeStream(parameterIndex, x, length);
			}
		} else {
			this.pstmt.setUnicodeStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setBinaryStream(parameterIndex, x, length);
			}
		} else {
			this.pstmt.setBinaryStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
/**
 * 파라미터를 지운다.
 */
	public void clearParameters() throws SQLException {
		this._al.clear();
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].clearParameters();
			}
		} else {
			this.pstmt.clearParameters();
		}
	}
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setObject(parameterIndex, x, targetSqlType);
			}
		} else {
			this.pstmt.setObject(parameterIndex, x, targetSqlType);
		}
		setVariable(parameterIndex, x);
	}
	public void setObject(int parameterIndex, Object x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setObject(parameterIndex, x);
			}
		} else {
			this.pstmt.setObject(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute() throws SQLException {
		try {
			boolean issucess = false;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					issucess = this.pstmts[i].execute();
				}
			} else {
				issucess = this.pstmt.execute();
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(this.getQueryString()); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(this.getQueryString()); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public void addBatch() throws SQLException {
		try {
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					this.pstmts[i].addBatch();
				}
			} else {
				this.pstmt.addBatch();
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(this.getQueryString()); }
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(this.getQueryString()); }
			throw e;
		}
	}
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setCharacterStream(parameterIndex, reader, length);
			}
		} else {
			this.pstmt.setCharacterStream(parameterIndex, reader, length);
		}
		setVariable(parameterIndex, reader);
	}
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setRef(parameterIndex, x);
			}
		} else {
			this.pstmt.setRef(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setBlob(parameterIndex, x);
			}
		} else {
			this.pstmt.setBlob(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setClob(parameterIndex, x);
			}
		} else {
			this.pstmt.setClob(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setArray(int parameterIndex, Array x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setArray(parameterIndex, x);
			}
		} else {
			this.pstmt.setArray(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public ResultSetMetaData getMetaData() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getMetaData();
		} else {
			return this.pstmt.getMetaData();
		}
	}
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setDate(parameterIndex, x, cal);
			}
		} else {
			this.pstmt.setDate(parameterIndex, x, cal);
		}
		setVariable(parameterIndex, x);
	}
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setTime(parameterIndex, x, cal);
			}
		} else {
			this.pstmt.setTime(parameterIndex, x, cal);
		}
		setVariable(parameterIndex, x);
	}
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setTimestamp(parameterIndex, x, cal);
			}
		} else {
			this.pstmt.setTimestamp(parameterIndex, x, cal);
		}
		setVariable(parameterIndex, x);
	}
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setNull(parameterIndex, sqlType, typeName);
			}
		} else {
			this.pstmt.setNull(parameterIndex, sqlType, typeName);
		}
		setVariable(parameterIndex, null);
	}
	public void setURL(int parameterIndex, URL x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setURL(parameterIndex, x);
			}
		} else {
			this.pstmt.setURL(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public ParameterMetaData getParameterMetaData() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getParameterMetaData();
		} else {
			return this.pstmt.getParameterMetaData();
		}
	}
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setRowId(parameterIndex, x);
			}
		} else {
			this.pstmt.setRowId(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setNString(int parameterIndex, String value) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setNString(parameterIndex, value);
			}
		} else {
			this.pstmt.setNString(parameterIndex, value);
		}
		setVariable(parameterIndex, value);
	}
	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setNCharacterStream(parameterIndex, value, length);
			}
		} else {
			this.pstmt.setNCharacterStream(parameterIndex, value, length);
		}
		setVariable(parameterIndex, value);
	}
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setNClob(parameterIndex, value);
			}
		} else {
			this.pstmt.setNClob(parameterIndex, value);
		}
		setVariable(parameterIndex, value);
	}
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setClob(parameterIndex, reader, length);
			}
		} else {
			this.pstmt.setClob(parameterIndex, reader, length);
		}
		setVariable(parameterIndex, reader);
	}
	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setBlob(parameterIndex, inputStream, length);
			}
		} else {
			this.pstmt.setBlob(parameterIndex, inputStream, length);
		}
		setVariable(parameterIndex, inputStream);
	}
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setNClob(parameterIndex, reader, length);
			}
		} else {
			this.pstmt.setNClob(parameterIndex, reader, length);
		}
		setVariable(parameterIndex, reader);
	}
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setSQLXML(parameterIndex, xmlObject);
			}
		} else {
			this.pstmt.setSQLXML(parameterIndex, xmlObject);
		}
		setVariable(parameterIndex, xmlObject);
	}
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setObject(parameterIndex, x, targetSqlType, scaleOrLength);
			}
		} else {
			this.pstmt.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
		}
		setVariable(parameterIndex, x);
	}
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setAsciiStream(parameterIndex, x, length);
			}
		} else {
			this.pstmt.setAsciiStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setBinaryStream(parameterIndex, x, length);
			}
		} else {
			this.pstmt.setBinaryStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setCharacterStream(parameterIndex, reader, length);
			}
		} else {
			this.pstmt.setCharacterStream(parameterIndex, reader, length);
		}
		setVariable(parameterIndex, reader);
	}
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setAsciiStream(parameterIndex, x);
			}
		} else {
			this.pstmt.setAsciiStream(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setBinaryStream(parameterIndex, x);
			}
		} else {
			this.pstmt.setBinaryStream(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setCharacterStream(parameterIndex, reader);
			}
		} else {
			this.pstmt.setCharacterStream(parameterIndex, reader);
		}
		setVariable(parameterIndex, reader);
	}
	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setNCharacterStream(parameterIndex, value);
			}
		} else {
			this.pstmt.setNCharacterStream(parameterIndex, value);
		}
		setVariable(parameterIndex, value);
	}
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setClob(parameterIndex, reader);
			}
		} else {
			this.pstmt.setClob(parameterIndex, reader);
		}
		setVariable(parameterIndex, reader);
	}
	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setBlob(parameterIndex, inputStream);
			}
		} else {
			this.pstmt.setBlob(parameterIndex, inputStream);
		}
		setVariable(parameterIndex, inputStream);
	}
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setNClob(parameterIndex, reader);
			}
		} else {
			this.pstmt.setNClob(parameterIndex, reader);
		}
		setVariable(parameterIndex, reader);
	}
@Override
	public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setObject(parameterIndex, x, targetSqlType, scaleOrLength);
			}
		} else {
			this.pstmt.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
		}
		setVariable(parameterIndex, x);
	}
@Override
	public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setObject(parameterIndex, x, targetSqlType);
			}
		} else {
			this.pstmt.setObject(parameterIndex, x, targetSqlType);
		}
		setVariable(parameterIndex, x);
	}
@Override
	public long executeLargeUpdate() throws SQLException {
		try {
			long result = 0;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeLargeUpdate();
				}
			} else {
				result = this.pstmt.executeLargeUpdate();
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(this.getQueryString()); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(this.getQueryString()); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 첫 번째 것을 실행하고 반환받은 ResultSet 을 ResultSetImpl 로 감싸서 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public ResultSet executeQuery(String sql) throws SQLException {
		try {
			if(this.pstmts != null) {
				ResultSet rs = new ResultSetImpl(this, this.pstmts[0].executeQuery(sql), true);
				if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
				return rs;
			} else {
				ResultSet rs = new ResultSetImpl(this, this.pstmt.executeQuery(sql), false);
				if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
				return rs;
			}
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql) throws SQLException {
		try {
			if(this.pstmts != null) {
				int result = 0;
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate(sql);
				}
				if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
				return result;
			} else {
				int result = this.pstmt.executeUpdate(sql);
				if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
				return result;
			}
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * 객체를 닫는다.
 * @see kr.xdbc.trace.ConnectionManager.clear()
 */
	public void close() throws SQLException {
		ConnectionManager.getInstance().clear(this.index);
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].close();
			}
		} else {
			this.pstmt.close();
		}
	}
	public int getMaxFieldSize() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getMaxFieldSize();
		} else {
			return this.pstmt.getMaxFieldSize();
		}
	}
	public void setMaxFieldSize(int max) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setMaxFieldSize(max);
			}
		} else {
			this.pstmt.setMaxFieldSize(max);
		}
	}
	public int getMaxRows() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getMaxRows();
		} else {
			return this.pstmt.getMaxRows();
		}
	}
	public void setMaxRows(int max) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setMaxRows(max);
			}
		} else {
			this.pstmt.setMaxRows(max);
		}
	}
	public void setEscapeProcessing(boolean enable) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setEscapeProcessing(enable);
			}
		} else {
			this.pstmt.setEscapeProcessing(enable);
		}
	}
	public int getQueryTimeout() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getQueryTimeout();
		} else {
			return this.pstmt.getQueryTimeout();
		}
	}
	public void setQueryTimeout(int seconds) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setQueryTimeout(seconds);
			}
		} else {
			this.pstmt.setQueryTimeout(seconds);
		}
	}
	public void cancel() throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].cancel();
			}
		} else {
			this.pstmt.cancel();
		}
	}
	public SQLWarning getWarnings() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getWarnings();
		} else {
			return this.pstmt.getWarnings();
		}
	}
	public void clearWarnings() throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].clearWarnings();
			}
		} else {
			this.pstmt.clearWarnings();
		}
	}
	public void setCursorName(String name) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setCursorName(name);
			}
		} else {
			this.pstmt.setCursorName(name);
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql) throws SQLException {
		try {
			boolean issucess = false;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					issucess = this.pstmts[i].execute(sql);
				}
			} else {
				issucess = this.pstmt.execute(sql);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 첫 번째 것의 ResultSet 을 ResultSetImpl 로 감싸서 반환한다.
 */
	public ResultSet getResultSet() throws SQLException {
		if(this.pstmts != null) {
			return new ResultSetImpl(this, this.pstmts[0].getResultSet(), true);
		} else {
			return new ResultSetImpl(this, this.pstmt.getResultSet(), false);
		}
	}
	public int getUpdateCount() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getUpdateCount();
		} else {
			return this.pstmt.getUpdateCount();
		}
	}
	public boolean getMoreResults() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getMoreResults();
		} else {
			return this.pstmt.getMoreResults();
		}
	}
	public void setFetchDirection(int direction) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setFetchDirection(direction);
			}
		} else {
			this.pstmt.setFetchDirection(direction);
		}
	}
	public int getFetchDirection() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getFetchDirection();
		} else {
			return this.pstmt.getFetchDirection();
		}
	}
	public void setFetchSize(int rows) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setFetchSize(rows);
			}
		} else {
			this.pstmt.setFetchSize(rows);
		}
	}
	public int getFetchSize() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getFetchSize();
		} else {
			return this.pstmt.getFetchSize();
		}
	}
	public int getResultSetConcurrency() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getResultSetConcurrency();
		} else {
			return this.pstmt.getResultSetConcurrency();
		}
	}
	public int getResultSetType() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getResultSetType();
		} else {
			return this.pstmt.getResultSetType();
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public void addBatch(String sql) throws SQLException {
		try {
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					this.pstmts[i].addBatch(sql);
				}
			} else {
				this.pstmt.addBatch(sql);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public void clearBatch() throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].clearBatch();
			}
		} else {
			this.pstmt.clearBatch();
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 */
	public int[] executeBatch() throws SQLException {
		if(this.pstmts != null) {
			int[] r = new int[0];
			for(int i = 0; i < this.pstmts.length; i++) {
				r = this.pstmts[i].executeBatch();
			}
			return r;
		} else {
			return this.pstmt.executeBatch();
		}
	}
/**
 * ConnectionImpl 을 반환한다.
 */
	public Connection getConnection() throws SQLException {
		return this.con;
	}
	public boolean getMoreResults(int current) throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getMoreResults(current);
		} else {
			return this.pstmt.getMoreResults(current);
		}
	}
	public ResultSet getGeneratedKeys() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getGeneratedKeys();
		} else {
			return this.pstmt.getGeneratedKeys();
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			int result = 0;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate(sql, autoGeneratedKeys);
				}
			} else {
				result = this.pstmt.executeUpdate(sql, autoGeneratedKeys);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		try {
			int result = 0;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate(sql, columnIndexes);
				}
			} else {
				result = this.pstmt.executeUpdate(sql, columnIndexes);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		try {
			int result = 0;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeUpdate(sql, columnNames);
				}
			} else {
			result = this.pstmt.executeUpdate(sql, columnNames);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			boolean issucess = false;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					issucess = this.pstmts[i].execute(sql, autoGeneratedKeys);
				}
			} else {
				issucess = this.pstmt.execute(sql, autoGeneratedKeys);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		try {
			boolean issucess = false;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					issucess = this.pstmts[i].execute(sql, columnIndexes);
				}
			} else {
				issucess = this.pstmt.execute(sql, columnIndexes);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		try {
			boolean issucess = false;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					issucess = this.pstmts[i].execute(sql, columnNames);
				}
			} else {
				issucess = this.pstmt.execute(sql, columnNames);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public int getResultSetHoldability() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getResultSetHoldability();
		} else {
			return this.pstmt.getResultSetHoldability();
		}
	}
/**
 * 연결이 여러 개인 경우 모든 isClosed() 가 false 인 경우에만, false 를 반환한다.
 */
	public boolean isClosed() throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				if(this.pstmts[0].isClosed()) {
					return true;
				}
			}
			return false;
		} else {
			return this.pstmt.isClosed();
		}
	}
	public void setPoolable(boolean poolable) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setPoolable(poolable);
			}
		} else {
			this.pstmt.setPoolable(poolable);
		}
	}
	public boolean isPoolable() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].isPoolable();
		} else {
			return this.pstmt.isPoolable();
		}
	}
	public void closeOnCompletion() throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].closeOnCompletion();
			}
		} else {
			this.pstmt.closeOnCompletion();
		}
	}
	public boolean isCloseOnCompletion() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].isCloseOnCompletion();
		} else {
			return this.pstmt.isCloseOnCompletion();
		}
	}
@Override
	public long getLargeUpdateCount() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getLargeUpdateCount();
		} else {
			return this.pstmt.getLargeUpdateCount();
		}
	}
@Override
	public void setLargeMaxRows(long max) throws SQLException {
		if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				this.pstmts[i].setLargeMaxRows(max);
			}
		} else {
			this.pstmt.setLargeMaxRows(max);
		}
	}
@Override
	public long getLargeMaxRows() throws SQLException {
		if(this.pstmts != null) {
			return this.pstmts[0].getLargeMaxRows();
		} else {
			return this.pstmt.getLargeMaxRows();
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 */
@Override
	public long[] executeLargeBatch() throws SQLException {
		if(this.pstmts != null) {
			long[] r = new long[0];
			for(int i = 0; i < this.pstmts.length; i++) {
				r = this.pstmts[i].executeLargeBatch();
			}
			return r;
		} else {
			return this.pstmt.executeLargeBatch();
		}
		
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
@Override
	public long executeLargeUpdate(String sql) throws SQLException {
		try {
			long result = 0;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeLargeUpdate(sql);
				}
			} else {
				result = this.pstmt.executeLargeUpdate(sql);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
@Override
	public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			long result = 0;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeLargeUpdate(sql, autoGeneratedKeys);
				}
			} else {
				result = this.pstmt.executeLargeUpdate(sql, autoGeneratedKeys);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
@Override
	public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
		try {
			long result = 0;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeLargeUpdate(sql, columnIndexes);
				}
			} else {
				result = this.pstmt.executeLargeUpdate(sql, columnIndexes);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINER 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
@Override
	public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
		try {
			long result = 0;
			if(this.pstmts != null) {
				for(int i = 0; i < this.pstmts.length; i++) {
					result = this.pstmts[i].executeLargeUpdate(sql, columnNames);
				}
			} else {
			result = this.pstmt.executeLargeUpdate(sql, columnNames);
			}
			if(logger.isLoggable(Level.FINER)) { logger.finer(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * this.getClass() 를 먼저 반환한다.
 * 연결이 여러 개인 경우, 반환할 수 있는 것이 1개라도 있으면 반환한다.
 * 반환할 것이 없으면, 마지막 객체의 unwrap 결과를 반환한다.
 */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if(this.isClosed()) {
			throw new SQLException("This statement has been closed.");
		} else if(iface.isAssignableFrom(this.getClass())) {
			return iface.cast(this.getClass());
		} else if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				if(this.pstmts[i].isWrapperFor(iface)) {
					return this.pstmts[i].unwrap(iface);
				}
				if(i == this.pstmts.length - 1) {
					return this.pstmts[i].unwrap(iface);
				}
			}
			throw new SQLException("Cannot unwrap to " + iface.getName());
		} else {
			return this.pstmt.unwrap(iface);
		}
	}
/**
 * this.getClass() 이면 true 를 반환한다.
 * 연결이 여러개인 경우, 순차적으로 검사해서 1개라도 true 인 경우, true 를 반환한다.
 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		if(this.isClosed()) {
			throw new SQLException("This statement has been closed.");
		} else if(iface.isAssignableFrom(this.getClass())) {
			return true;
		} else if(this.pstmts != null) {
			for(int i = 0; i < this.pstmts.length; i++) {
				if(this.pstmts[i].isWrapperFor(iface)) {
					return true;
				}
			}
			return false;
		} else {
			return this.pstmt.isWrapperFor(iface);
		}
	}
}