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

package kr.xdbc.java7.sql;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Calendar;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Blob;
import java.sql.Ref;
import java.sql.Clob;
import java.sql.Array;
import java.sql.RowId;
import java.sql.NClob;
import java.sql.SQLXML;
import java.net.URL;
import java.io.InputStream;
import java.io.Reader;
import java.sql.ParameterMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLWarning;
import kr.xdbc.trace.ConnectionManager;
import kr.xdbc.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * CallableStatement Wrapper
 * API reference 로부터 자동으로 생성한 코드가 아닌 경우 javadoc 을 남긴다.

 * @author HeonJik, KIM
 * @version 0.3
 * @since 0.3
 */

public class CallableStatementImpl implements CallableStatement {
/**
 * ConnectionManager에서 관리하는 일련번호
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	private int index;
/**
 * Logger
 */
	private Logger logger = Logger.getLogger(this.getClass().getName());
/**
 * 내부적으로 관리하는 객체
 */
	private Connection con;
/**
 * 내부적으로 관리하는 객체
 */
	private CallableStatement cstmt;
/**
 * 내부적으로 관리하는 객체
 */
	private CallableStatement[] cstmts;
/**
 * SQL Query
 */
	private String sql = new String();
/**
 * 파라미터 Array
 */
	private ArrayList _al;
/**
 * 파라미터 HashMap(named paramter)
 */
	private HashMap _hm;
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	public CallableStatementImpl(Connection con, CallableStatement cstmt, String sql) {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.cstmt = cstmt;
		this.cstmts = null;
		this.sql = sql;
		this._al = new ArrayList();
		this._hm = new HashMap();
	}
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	public CallableStatementImpl(Connection con, CallableStatement[] cstmts, String sql) {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.cstmts = cstmts;
		this.sql = sql;
		this._al = new ArrayList();
		this._hm = new HashMap();
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
		return StringUtil.getQueryString(sql, this._al, this._hm);
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
 *변수를 Setting 한다.
 */
	public void setVariable(String parameterName, Object obj) {
		if(obj == null) {
			this._hm.put(parameterName, "null");
		} else if(obj instanceof Integer) {
			this._hm.put(parameterName, obj);
		} else {
			this._hm.put(parameterName, "'" + obj.toString() + "'");
		}
	}
	public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].registerOutParameter(parameterIndex, sqlType);
			}
		} else {
			this.cstmt.registerOutParameter(parameterIndex, sqlType);
		}
	}
	public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].registerOutParameter(parameterIndex, sqlType, scale);
			}
		} else {
			this.cstmt.registerOutParameter(parameterIndex, sqlType, scale);
		}
	}
	public boolean wasNull() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].wasNull();
		} else {
			return this.cstmt.wasNull();
		}
	}
	public String getString(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getString(parameterIndex);
		} else {
			return this.cstmt.getString(parameterIndex);
		}
	}
	public boolean getBoolean(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBoolean(parameterIndex);
		} else {
			return this.cstmt.getBoolean(parameterIndex);
		}
	}
	public byte getByte(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getByte(parameterIndex);
		} else {
			return this.cstmt.getByte(parameterIndex);
		}
	}
	public short getShort(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getShort(parameterIndex);
		} else {
			return this.cstmt.getShort(parameterIndex);
		}
	}
	public int getInt(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getInt(parameterIndex);
		} else {
			return this.cstmt.getInt(parameterIndex);
		}
	}
	public long getLong(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getLong(parameterIndex);
		} else {
			return this.cstmt.getLong(parameterIndex);
		}
	}
	public float getFloat(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getFloat(parameterIndex);
		} else {
			return this.cstmt.getFloat(parameterIndex);
		}
	}
	public double getDouble(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDouble(parameterIndex);
		} else {
			return this.cstmt.getDouble(parameterIndex);
		}
	}
@Deprecated
	public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBigDecimal(parameterIndex, scale);
		} else {
			return this.cstmt.getBigDecimal(parameterIndex, scale);
		}
	}
	public byte[] getBytes(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBytes(parameterIndex);
		} else {
			return this.cstmt.getBytes(parameterIndex);
		}
	}
	public Date getDate(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDate(parameterIndex);
		} else {
			return this.cstmt.getDate(parameterIndex);
		}
	}
	public Time getTime(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTime(parameterIndex);
		} else {
			return this.cstmt.getTime(parameterIndex);
		}
	}
	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTimestamp(parameterIndex);
		} else {
			return this.cstmt.getTimestamp(parameterIndex);
		}
	}
	public Object getObject(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(parameterIndex);
		} else {
			return this.cstmt.getObject(parameterIndex);
		}
	}
	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBigDecimal(parameterIndex);
		} else {
			return this.cstmt.getBigDecimal(parameterIndex);
		}
	}
	public Object getObject(int parameterIndex, Map<String,Class<?>> map) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(parameterIndex, map);
		} else {
			return this.cstmt.getObject(parameterIndex, map);
		}
	}
	public Ref getRef(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getRef(parameterIndex);
		} else {
			return this.cstmt.getRef(parameterIndex);
		}
	}
	public Blob getBlob(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBlob(parameterIndex);
		} else {
			return this.cstmt.getBlob(parameterIndex);
		}
	}
	public Clob getClob(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getClob(parameterIndex);
		} else {
			return this.cstmt.getClob(parameterIndex);
		}
	}
	public Array getArray(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getArray(parameterIndex);
		} else {
			return this.cstmt.getArray(parameterIndex);
		}
	}
	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDate(parameterIndex, cal);
		} else {
			return this.cstmt.getDate(parameterIndex, cal);
		}
	}
	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTime(parameterIndex, cal);
		} else {
			return this.cstmt.getTime(parameterIndex, cal);
		}
	}
	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTimestamp(parameterIndex, cal);
		} else {
			return this.cstmt.getTimestamp(parameterIndex, cal);
		}
	}
	public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].registerOutParameter(parameterIndex, sqlType, typeName);
			}
		} else {
			this.cstmt.registerOutParameter(parameterIndex, sqlType, typeName);
		}
	}
	public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].registerOutParameter(parameterName, sqlType);
			}
		} else {
			this.cstmt.registerOutParameter(parameterName, sqlType);
		}
	}
	public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].registerOutParameter(parameterName, sqlType, scale);
			}
		} else {
			this.cstmt.registerOutParameter(parameterName, sqlType, scale);
		}
	}
	public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].registerOutParameter(parameterName, sqlType, typeName);
			}
		} else {
			this.cstmt.registerOutParameter(parameterName, sqlType, typeName);
		}
	}
	public URL getURL(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getURL(parameterIndex);
		} else {
			return this.cstmt.getURL(parameterIndex);
		}
	}
	public void setURL(String parameterName, URL val) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setURL(parameterName, val);
			}
		} else {
			this.cstmt.setURL(parameterName, val);
		}
		setVariable(parameterName, val);
	}
	public void setNull(String parameterName, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNull(parameterName, sqlType);
			}
		} else {
			this.cstmt.setNull(parameterName, sqlType);
		}
		setVariable(parameterName, null);
	}
	public void setBoolean(String parameterName, boolean x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBoolean(parameterName, x);
			}
		} else {
			this.cstmt.setBoolean(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setByte(String parameterName, byte x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setByte(parameterName, x);
			}
		} else {
			this.cstmt.setByte(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setShort(String parameterName, short x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setShort(parameterName, x);
			}
		} else {
			this.cstmt.setShort(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setInt(String parameterName, int x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setInt(parameterName, x);
			}
		} else {
			this.cstmt.setInt(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setLong(String parameterName, long x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setLong(parameterName, x);
			}
		} else {
			this.cstmt.setLong(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setFloat(String parameterName, float x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setFloat(parameterName, x);
			}
		} else {
			this.cstmt.setFloat(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setDouble(String parameterName, double x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setDouble(parameterName, x);
			}
		} else {
			this.cstmt.setDouble(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBigDecimal(parameterName, x);
			}
		} else {
			this.cstmt.setBigDecimal(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setString(String parameterName, String x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setString(parameterName, x);
			}
		} else {
			this.cstmt.setString(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setBytes(String parameterName, byte[] x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBytes(parameterName, x);
			}
		} else {
			this.cstmt.setBytes(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setDate(String parameterName, Date x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setDate(parameterName, x);
			}
		} else {
			this.cstmt.setDate(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setTime(String parameterName, Time x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setTime(parameterName, x);
			}
		} else {
			this.cstmt.setTime(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setTimestamp(parameterName, x);
			}
		} else {
			this.cstmt.setTimestamp(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setAsciiStream(parameterName, x, length);
			}
		} else {
			this.cstmt.setAsciiStream(parameterName, x, length);
		}
		setVariable(parameterName, x);
	}
	public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBinaryStream(parameterName, x, length);
			}
		} else {
			this.cstmt.setBinaryStream(parameterName, x, length);
		}
		setVariable(parameterName, x);
	}
	public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setObject(parameterName, x, targetSqlType, scale);
			}
		} else {
			this.cstmt.setObject(parameterName, x, targetSqlType, scale);
		}
		setVariable(parameterName, x);
	}
	public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setObject(parameterName, x, targetSqlType);
			}
		} else {
			this.cstmt.setObject(parameterName, x, targetSqlType);
		}
		setVariable(parameterName, x);
	}
	public void setObject(String parameterName, Object x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setObject(parameterName, x);
			}
		} else {
			this.cstmt.setObject(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setCharacterStream(parameterName, reader, length);
			}
		} else {
			this.cstmt.setCharacterStream(parameterName, reader, length);
		}
		setVariable(parameterName, reader);
	}
	public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setDate(parameterName, x, cal);
			}
		} else {
			this.cstmt.setDate(parameterName, x, cal);
		}
		setVariable(parameterName, x);
	}
	public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setTime(parameterName, x, cal);
			}
		} else {
			this.cstmt.setTime(parameterName, x, cal);
		}
		setVariable(parameterName, x);
	}
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setTimestamp(parameterName, x, cal);
			}
		} else {
			this.cstmt.setTimestamp(parameterName, x, cal);
		}
		setVariable(parameterName, x);
	}
	public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNull(parameterName, sqlType, typeName);
			}
		} else {
			this.cstmt.setNull(parameterName, sqlType, typeName);
		}
		setVariable(parameterName, null);
	}
	public String getString(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getString(parameterName);
		} else {
			return this.cstmt.getString(parameterName);
		}
	}
	public boolean getBoolean(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBoolean(parameterName);
		} else {
			return this.cstmt.getBoolean(parameterName);
		}
	}
	public byte getByte(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getByte(parameterName);
		} else {
			return this.cstmt.getByte(parameterName);
		}
	}
	public short getShort(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getShort(parameterName);
		} else {
			return this.cstmt.getShort(parameterName);
		}
	}
	public int getInt(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getInt(parameterName);
		} else {
			return this.cstmt.getInt(parameterName);
		}
	}
	public long getLong(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getLong(parameterName);
		} else {
			return this.cstmt.getLong(parameterName);
		}
	}
	public float getFloat(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getFloat(parameterName);
		} else {
			return this.cstmt.getFloat(parameterName);
		}
	}
	public double getDouble(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDouble(parameterName);
		} else {
			return this.cstmt.getDouble(parameterName);
		}
	}
	public byte[] getBytes(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBytes(parameterName);
		} else {
			return this.cstmt.getBytes(parameterName);
		}
	}
	public Date getDate(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDate(parameterName);
		} else {
			return this.cstmt.getDate(parameterName);
		}
	}
	public Time getTime(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTime(parameterName);
		} else {
			return this.cstmt.getTime(parameterName);
		}
	}
	public Timestamp getTimestamp(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTimestamp(parameterName);
		} else {
			return this.cstmt.getTimestamp(parameterName);
		}
	}
	public Object getObject(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(parameterName);
		} else {
			return this.cstmt.getObject(parameterName);
		}
	}
	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBigDecimal(parameterName);
		} else {
			return this.cstmt.getBigDecimal(parameterName);
		}
	}
	public Object getObject(String parameterName, Map<String,Class<?>> map) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(parameterName, map);
		} else {
			return this.cstmt.getObject(parameterName, map);
		}
	}
	public Ref getRef(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getRef(parameterName);
		} else {
			return this.cstmt.getRef(parameterName);
		}
	}
	public Blob getBlob(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBlob(parameterName);
		} else {
			return this.cstmt.getBlob(parameterName);
		}
	}
	public Clob getClob(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getClob(parameterName);
		} else {
			return this.cstmt.getClob(parameterName);
		}
	}
	public Array getArray(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getArray(parameterName);
		} else {
			return this.cstmt.getArray(parameterName);
		}
	}
	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDate(parameterName, cal);
		} else {
			return this.cstmt.getDate(parameterName, cal);
		}
	}
	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTime(parameterName, cal);
		} else {
			return this.cstmt.getTime(parameterName, cal);
		}
	}
	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTimestamp(parameterName, cal);
		} else {
			return this.cstmt.getTimestamp(parameterName, cal);
		}
	}
	public URL getURL(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getURL(parameterName);
		} else {
			return this.cstmt.getURL(parameterName);
		}
	}
	public RowId getRowId(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getRowId(parameterIndex);
		} else {
			return this.cstmt.getRowId(parameterIndex);
		}
	}
	public RowId getRowId(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getRowId(parameterName);
		} else {
			return this.cstmt.getRowId(parameterName);
		}
	}
	public void setRowId(String parameterName, RowId x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setRowId(parameterName, x);
			}
		} else {
			this.cstmt.setRowId(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setNString(String parameterName, String value) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNString(parameterName, value);
			}
		} else {
			this.cstmt.setNString(parameterName, value);
		}
		setVariable(parameterName, value);
	}
	public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNCharacterStream(parameterName, value, length);
			}
		} else {
			this.cstmt.setNCharacterStream(parameterName, value, length);
		}
		setVariable(parameterName, value);
	}
	public void setNClob(String parameterName, NClob value) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNClob(parameterName, value);
			}
		} else {
			this.cstmt.setNClob(parameterName, value);
		}
		setVariable(parameterName, value);
	}
	public void setClob(String parameterName, Reader reader, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setClob(parameterName, reader, length);
			}
		} else {
			this.cstmt.setClob(parameterName, reader, length);
		}
		setVariable(parameterName, reader);
	}
	public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBlob(parameterName, inputStream, length);
			}
		} else {
			this.cstmt.setBlob(parameterName, inputStream, length);
		}
		setVariable(parameterName, inputStream);
	}
	public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNClob(parameterName, reader, length);
			}
		} else {
			this.cstmt.setNClob(parameterName, reader, length);
		}
		setVariable(parameterName, reader);
	}
	public NClob getNClob(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNClob(parameterIndex);
		} else {
			return this.cstmt.getNClob(parameterIndex);
		}
	}
	public NClob getNClob(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNClob(parameterName);
		} else {
			return this.cstmt.getNClob(parameterName);
		}
	}
	public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setSQLXML(parameterName, xmlObject);
			}
		} else {
			this.cstmt.setSQLXML(parameterName, xmlObject);
		}
		setVariable(parameterName, xmlObject);
	}
	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getSQLXML(parameterIndex);
		} else {
			return this.cstmt.getSQLXML(parameterIndex);
		}
	}
	public SQLXML getSQLXML(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getSQLXML(parameterName);
		} else {
			return this.cstmt.getSQLXML(parameterName);
		}
	}
	public String getNString(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNString(parameterIndex);
		} else {
			return this.cstmt.getNString(parameterIndex);
		}
	}
	public String getNString(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNString(parameterName);
		} else {
			return this.cstmt.getNString(parameterName);
		}
	}
	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNCharacterStream(parameterIndex);
		} else {
			return this.cstmt.getNCharacterStream(parameterIndex);
		}
	}
	public Reader getNCharacterStream(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNCharacterStream(parameterName);
		} else {
			return this.cstmt.getNCharacterStream(parameterName);
		}
	}
	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getCharacterStream(parameterIndex);
		} else {
			return this.cstmt.getCharacterStream(parameterIndex);
		}
	}
	public Reader getCharacterStream(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getCharacterStream(parameterName);
		} else {
			return this.cstmt.getCharacterStream(parameterName);
		}
	}
	public void setBlob(String parameterName, Blob x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBlob(parameterName, x);
			}
		} else {
			this.cstmt.setBlob(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setClob(String parameterName, Clob x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setClob(parameterName, x);
			}
		} else {
			this.cstmt.setClob(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setAsciiStream(parameterName, x, length);
			}
		} else {
			this.cstmt.setAsciiStream(parameterName, x, length);
		}
		setVariable(parameterName, x);
	}
	public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBinaryStream(parameterName, x, length);
			}
		} else {
			this.cstmt.setBinaryStream(parameterName, x, length);
		}
		setVariable(parameterName, x);
	}
	public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setCharacterStream(parameterName, reader, length);
			}
		} else {
			this.cstmt.setCharacterStream(parameterName, reader, length);
		}
		setVariable(parameterName, reader);
	}
	public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setAsciiStream(parameterName, x);
			}
		} else {
			this.cstmt.setAsciiStream(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBinaryStream(parameterName, x);
			}
		} else {
			this.cstmt.setBinaryStream(parameterName, x);
		}
		setVariable(parameterName, x);
	}
	public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setCharacterStream(parameterName, reader);
			}
		} else {
			this.cstmt.setCharacterStream(parameterName, reader);
		}
		setVariable(parameterName, reader);
	}
	public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNCharacterStream(parameterName, value);
			}
		} else {
			this.cstmt.setNCharacterStream(parameterName, value);
		}
		setVariable(parameterName, value);
	}
	public void setClob(String parameterName, Reader reader) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setClob(parameterName, reader);
			}
		} else {
			this.cstmt.setClob(parameterName, reader);
		}
		setVariable(parameterName, reader);
	}
	public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBlob(parameterName, inputStream);
			}
		} else {
			this.cstmt.setBlob(parameterName, inputStream);
		}
		setVariable(parameterName, inputStream);
	}
	public void setNClob(String parameterName, Reader reader) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNClob(parameterName, reader);
			}
		} else {
			this.cstmt.setNClob(parameterName, reader);
		}
		setVariable(parameterName, reader);
	}
	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(parameterIndex, type);
		} else {
			return this.cstmt.getObject(parameterIndex, type);
		}
	}
	public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(parameterName, type);
		} else {
			return this.cstmt.getObject(parameterName, type);
		}
	}
/**
 * Statement 가 여러 개인 경우 첫 번째 것을 실행하고 반환받은 ResultSet 을 ResultSetImpl 로 감싸서 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public ResultSet executeQuery() throws SQLException {
		try {
			if(this.cstmts != null) {
				ResultSet rs = new ResultSetImpl(this, this.cstmts[0].executeQuery(), true);
				if(logger.isLoggable(Level.FINE)) { logger.fine(this.getQueryString()); }
				return rs;
			} else {
				ResultSet rs = new ResultSetImpl(this, this.cstmt.executeQuery(), false);
				if(logger.isLoggable(Level.FINE)) { logger.fine(this.getQueryString()); }
				return rs;
			}
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(this.getQueryString()); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate() throws SQLException {
		try {
			int result = 0;
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					result = this.cstmts[i].executeUpdate();
				}
			} else {
				result = this.cstmt.executeUpdate();
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(this.getQueryString()); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(this.getQueryString()); }
			throw e;
		}
	}
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNull(parameterIndex, sqlType);
			}
		} else {
			this.cstmt.setNull(parameterIndex, sqlType);
		}
		setVariable(parameterIndex, null);
	}
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBoolean(parameterIndex, x);
			}
		} else {
			this.cstmt.setBoolean(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setByte(int parameterIndex, byte x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setByte(parameterIndex, x);
			}
		} else {
			this.cstmt.setByte(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setShort(int parameterIndex, short x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setShort(parameterIndex, x);
			}
		} else {
			this.cstmt.setShort(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setInt(int parameterIndex, int x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setInt(parameterIndex, x);
			}
		} else {
			this.cstmt.setInt(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setLong(int parameterIndex, long x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setLong(parameterIndex, x);
			}
		} else {
			this.cstmt.setLong(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setFloat(int parameterIndex, float x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setFloat(parameterIndex, x);
			}
		} else {
			this.cstmt.setFloat(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setDouble(int parameterIndex, double x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setDouble(parameterIndex, x);
			}
		} else {
			this.cstmt.setDouble(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBigDecimal(parameterIndex, x);
			}
		} else {
			this.cstmt.setBigDecimal(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setString(int parameterIndex, String x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setString(parameterIndex, x);
			}
		} else {
			this.cstmt.setString(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBytes(parameterIndex, x);
			}
		} else {
			this.cstmt.setBytes(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setDate(int parameterIndex, Date x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setDate(parameterIndex, x);
			}
		} else {
			this.cstmt.setDate(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setTime(int parameterIndex, Time x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setTime(parameterIndex, x);
			}
		} else {
			this.cstmt.setTime(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setTimestamp(parameterIndex, x);
			}
		} else {
			this.cstmt.setTimestamp(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setAsciiStream(parameterIndex, x, length);
			}
		} else {
			this.cstmt.setAsciiStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
@Deprecated
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setUnicodeStream(parameterIndex, x, length);
			}
		} else {
			this.cstmt.setUnicodeStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBinaryStream(parameterIndex, x, length);
			}
		} else {
			this.cstmt.setBinaryStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
/**
 * 파라미터를 지운다.
 */
	public void clearParameters() throws SQLException {
		this._al.clear();
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].clearParameters();
			}
		} else {
			this.cstmt.clearParameters();
		}
	}
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setObject(parameterIndex, x, targetSqlType);
			}
		} else {
			this.cstmt.setObject(parameterIndex, x, targetSqlType);
		}
		setVariable(parameterIndex, x);
	}
	public void setObject(int parameterIndex, Object x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setObject(parameterIndex, x);
			}
		} else {
			this.cstmt.setObject(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute() throws SQLException {
		try {
			boolean issucess = false;
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					issucess = this.cstmts[i].execute();
				}
			} else {
				issucess = this.cstmt.execute();
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(this.getQueryString()); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(this.getQueryString()); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public void addBatch() throws SQLException {
		try {
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					this.cstmts[i].addBatch();
				}
			} else {
				this.cstmt.addBatch();
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(this.getQueryString()); }
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(this.getQueryString()); }
			throw e;
		}
	}
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setCharacterStream(parameterIndex, reader, length);
			}
		} else {
			this.cstmt.setCharacterStream(parameterIndex, reader, length);
		}
		setVariable(parameterIndex, reader);
	}
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setRef(parameterIndex, x);
			}
		} else {
			this.cstmt.setRef(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBlob(parameterIndex, x);
			}
		} else {
			this.cstmt.setBlob(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setClob(parameterIndex, x);
			}
		} else {
			this.cstmt.setClob(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setArray(int parameterIndex, Array x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setArray(parameterIndex, x);
			}
		} else {
			this.cstmt.setArray(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public ResultSetMetaData getMetaData() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getMetaData();
		} else {
			return this.cstmt.getMetaData();
		}
	}
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setDate(parameterIndex, x, cal);
			}
		} else {
			this.cstmt.setDate(parameterIndex, x, cal);
		}
		setVariable(parameterIndex, x);
	}
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setTime(parameterIndex, x, cal);
			}
		} else {
			this.cstmt.setTime(parameterIndex, x, cal);
		}
		setVariable(parameterIndex, x);
	}
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setTimestamp(parameterIndex, x, cal);
			}
		} else {
			this.cstmt.setTimestamp(parameterIndex, x, cal);
		}
		setVariable(parameterIndex, x);
	}
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNull(parameterIndex, sqlType, typeName);
			}
		} else {
			this.cstmt.setNull(parameterIndex, sqlType, typeName);
		}
		setVariable(parameterIndex, null);
	}
	public void setURL(int parameterIndex, URL x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setURL(parameterIndex, x);
			}
		} else {
			this.cstmt.setURL(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public ParameterMetaData getParameterMetaData() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getParameterMetaData();
		} else {
			return this.cstmt.getParameterMetaData();
		}
	}
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setRowId(parameterIndex, x);
			}
		} else {
			this.cstmt.setRowId(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setNString(int parameterIndex, String value) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNString(parameterIndex, value);
			}
		} else {
			this.cstmt.setNString(parameterIndex, value);
		}
		setVariable(parameterIndex, value);
	}
	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNCharacterStream(parameterIndex, value, length);
			}
		} else {
			this.cstmt.setNCharacterStream(parameterIndex, value, length);
		}
		setVariable(parameterIndex, value);
	}
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNClob(parameterIndex, value);
			}
		} else {
			this.cstmt.setNClob(parameterIndex, value);
		}
		setVariable(parameterIndex, value);
	}
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setClob(parameterIndex, reader, length);
			}
		} else {
			this.cstmt.setClob(parameterIndex, reader, length);
		}
		setVariable(parameterIndex, reader);
	}
	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBlob(parameterIndex, inputStream, length);
			}
		} else {
			this.cstmt.setBlob(parameterIndex, inputStream, length);
		}
		setVariable(parameterIndex, inputStream);
	}
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNClob(parameterIndex, reader, length);
			}
		} else {
			this.cstmt.setNClob(parameterIndex, reader, length);
		}
		setVariable(parameterIndex, reader);
	}
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setSQLXML(parameterIndex, xmlObject);
			}
		} else {
			this.cstmt.setSQLXML(parameterIndex, xmlObject);
		}
		setVariable(parameterIndex, xmlObject);
	}
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setObject(parameterIndex, x, targetSqlType, scaleOrLength);
			}
		} else {
			this.cstmt.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
		}
		setVariable(parameterIndex, x);
	}
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setAsciiStream(parameterIndex, x, length);
			}
		} else {
			this.cstmt.setAsciiStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBinaryStream(parameterIndex, x, length);
			}
		} else {
			this.cstmt.setBinaryStream(parameterIndex, x, length);
		}
		setVariable(parameterIndex, x);
	}
	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setCharacterStream(parameterIndex, reader, length);
			}
		} else {
			this.cstmt.setCharacterStream(parameterIndex, reader, length);
		}
		setVariable(parameterIndex, reader);
	}
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setAsciiStream(parameterIndex, x);
			}
		} else {
			this.cstmt.setAsciiStream(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBinaryStream(parameterIndex, x);
			}
		} else {
			this.cstmt.setBinaryStream(parameterIndex, x);
		}
		setVariable(parameterIndex, x);
	}
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setCharacterStream(parameterIndex, reader);
			}
		} else {
			this.cstmt.setCharacterStream(parameterIndex, reader);
		}
		setVariable(parameterIndex, reader);
	}
	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNCharacterStream(parameterIndex, value);
			}
		} else {
			this.cstmt.setNCharacterStream(parameterIndex, value);
		}
		setVariable(parameterIndex, value);
	}
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setClob(parameterIndex, reader);
			}
		} else {
			this.cstmt.setClob(parameterIndex, reader);
		}
		setVariable(parameterIndex, reader);
	}
	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setBlob(parameterIndex, inputStream);
			}
		} else {
			this.cstmt.setBlob(parameterIndex, inputStream);
		}
		setVariable(parameterIndex, inputStream);
	}
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setNClob(parameterIndex, reader);
			}
		} else {
			this.cstmt.setNClob(parameterIndex, reader);
		}
		setVariable(parameterIndex, reader);
	}
/**
 * Statement 가 여러 개인 경우 첫 번째 것을 실행하고 반환받은 ResultSet 을 ResultSetImpl 로 감싸서 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public ResultSet executeQuery(String sql) throws SQLException {
		try {
			if(this.cstmts != null) {
				ResultSet rs = new ResultSetImpl(this, this.cstmts[0].executeQuery(sql), true);
				if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
				return rs;
			} else {
				ResultSet rs = new ResultSetImpl(this, this.cstmt.executeQuery(sql), false);
				if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
				return rs;
			}
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql) throws SQLException {
		try {
			if(this.cstmts != null) {
				int result = 0;
				for(int i = 0; i < this.cstmts.length; i++) {
					result = this.cstmts[i].executeUpdate(sql);
				}
				if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
				return result;
			} else {
				int result = this.cstmt.executeUpdate(sql);
				if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
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
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].close();
			}
		} else {
			this.cstmt.close();
		}
	}
	public int getMaxFieldSize() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getMaxFieldSize();
		} else {
			return this.cstmt.getMaxFieldSize();
		}
	}
	public void setMaxFieldSize(int max) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setMaxFieldSize(max);
			}
		} else {
			this.cstmt.setMaxFieldSize(max);
		}
	}
	public int getMaxRows() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getMaxRows();
		} else {
			return this.cstmt.getMaxRows();
		}
	}
	public void setMaxRows(int max) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setMaxRows(max);
			}
		} else {
			this.cstmt.setMaxRows(max);
		}
	}
	public void setEscapeProcessing(boolean enable) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setEscapeProcessing(enable);
			}
		} else {
			this.cstmt.setEscapeProcessing(enable);
		}
	}
	public int getQueryTimeout() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getQueryTimeout();
		} else {
			return this.cstmt.getQueryTimeout();
		}
	}
	public void setQueryTimeout(int seconds) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setQueryTimeout(seconds);
			}
		} else {
			this.cstmt.setQueryTimeout(seconds);
		}
	}
	public void cancel() throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].cancel();
			}
		} else {
			this.cstmt.cancel();
		}
	}
	public SQLWarning getWarnings() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getWarnings();
		} else {
			return this.cstmt.getWarnings();
		}
	}
	public void clearWarnings() throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].clearWarnings();
			}
		} else {
			this.cstmt.clearWarnings();
		}
	}
	public void setCursorName(String name) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setCursorName(name);
			}
		} else {
			this.cstmt.setCursorName(name);
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql) throws SQLException {
		try {
			boolean issucess = false;
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					issucess = this.cstmts[i].execute(sql);
				}
			} else {
				issucess = this.cstmt.execute(sql);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
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
		if(this.cstmts != null) {
			return new ResultSetImpl(this, this.cstmts[0].getResultSet(), true);
		} else {
			return new ResultSetImpl(this, this.cstmt.getResultSet(), false);
		}
	}
	public int getUpdateCount() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getUpdateCount();
		} else {
			return this.cstmt.getUpdateCount();
		}
	}
	public boolean getMoreResults() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getMoreResults();
		} else {
			return this.cstmt.getMoreResults();
		}
	}
	public void setFetchDirection(int direction) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setFetchDirection(direction);
			}
		} else {
			this.cstmt.setFetchDirection(direction);
		}
	}
	public int getFetchDirection() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getFetchDirection();
		} else {
			return this.cstmt.getFetchDirection();
		}
	}
	public void setFetchSize(int rows) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setFetchSize(rows);
			}
		} else {
			this.cstmt.setFetchSize(rows);
		}
	}
	public int getFetchSize() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getFetchSize();
		} else {
			return this.cstmt.getFetchSize();
		}
	}
	public int getResultSetConcurrency() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getResultSetConcurrency();
		} else {
			return this.cstmt.getResultSetConcurrency();
		}
	}
	public int getResultSetType() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getResultSetType();
		} else {
			return this.cstmt.getResultSetType();
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public void addBatch(String sql) throws SQLException {
		try {
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					this.cstmts[i].addBatch(sql);
				}
			} else {
				this.cstmt.addBatch(sql);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public void clearBatch() throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].clearBatch();
			}
		} else {
			this.cstmt.clearBatch();
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 */
	public int[] executeBatch() throws SQLException {
		if(this.cstmts != null) {
			int[] r = new int[0];
			for(int i = 0; i < this.cstmts.length; i++) {
				r = this.cstmts[i].executeBatch();
			}
			return r;
		} else {
			return this.cstmt.executeBatch();
		}
	}
/**
 * ConnectionImpl 을 반환한다.
 */
	public Connection getConnection() throws SQLException {
		return this.con;
	}
	public boolean getMoreResults(int current) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getMoreResults(current);
		} else {
			return this.cstmt.getMoreResults(current);
		}
	}
	public ResultSet getGeneratedKeys() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getGeneratedKeys();
		} else {
			return this.cstmt.getGeneratedKeys();
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			int result = 0;
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					result = this.cstmts[i].executeUpdate(sql, autoGeneratedKeys);
				}
			} else {
				result = this.cstmt.executeUpdate(sql, autoGeneratedKeys);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		try {
			int result = 0;
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					result = this.cstmts[i].executeUpdate(sql, columnIndexes);
				}
			} else {
				result = this.cstmt.executeUpdate(sql, columnIndexes);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		try {
			int result = 0;
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					result = this.cstmts[i].executeUpdate(sql, columnNames);
				}
			} else {
			result = this.cstmt.executeUpdate(sql, columnNames);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			boolean issucess = false;
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					issucess = this.cstmts[i].execute(sql, autoGeneratedKeys);
				}
			} else {
				issucess = this.cstmt.execute(sql, autoGeneratedKeys);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		try {
			boolean issucess = false;
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					issucess = this.cstmts[i].execute(sql, columnIndexes);
				}
			} else {
				issucess = this.cstmt.execute(sql, columnIndexes);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		try {
			boolean issucess = false;
			if(this.cstmts != null) {
				for(int i = 0; i < this.cstmts.length; i++) {
					issucess = this.cstmts[i].execute(sql, columnNames);
				}
			} else {
				issucess = this.cstmt.execute(sql, columnNames);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public int getResultSetHoldability() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getResultSetHoldability();
		} else {
			return this.cstmt.getResultSetHoldability();
		}
	}
/**
 * 연결이 여러 개인 경우 모든 isClosed() 가 false 인 경우에만, false 를 반환한다.
 */
	public boolean isClosed() throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(this.cstmts[0].isClosed()) {
					return true;
				}
			}
			return false;
		} else {
			return this.cstmt.isClosed();
		}
	}
	public void setPoolable(boolean poolable) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].setPoolable(poolable);
			}
		} else {
			this.cstmt.setPoolable(poolable);
		}
	}
	public boolean isPoolable() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].isPoolable();
		} else {
			return this.cstmt.isPoolable();
		}
	}
	public void closeOnCompletion() throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				this.cstmts[i].closeOnCompletion();
			}
		} else {
			this.cstmt.closeOnCompletion();
		}
	}
	public boolean isCloseOnCompletion() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].isCloseOnCompletion();
		} else {
			return this.cstmt.isCloseOnCompletion();
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
		} else if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(this.cstmts[i].isWrapperFor(iface)) {
					return this.cstmts[i].unwrap(iface);
				}
				if(i == this.cstmts.length - 1) {
					return this.cstmts[i].unwrap(iface);
				}
			}
			throw new SQLException("Cannot unwrap to " + iface.getName());
		} else {
			return this.cstmt.unwrap(iface);
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
		} else if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(this.cstmts[i].isWrapperFor(iface)) {
					return true;
				}
			}
			return false;
		} else {
			return this.cstmt.isWrapperFor(iface);
		}
	}
}
