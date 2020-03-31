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
import java.sql.Blob;
import java.sql.Ref;
import java.sql.Clob;
import java.sql.Array;
import java.net.URL;
import java.io.InputStream;
import java.io.Reader;
import java.sql.ParameterMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLWarning;
import kr.xdbc.trace.ConnectionManager;

/**
 * CallableStatement Wrapper

 * @author HeonJik, KIM
 * @version 0.2
 * @since 0.1
 */

public class CallableStatementImpl implements CallableStatement {
	private int index;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Connection con;
	private CallableStatement cstmt;
	private CallableStatement[] cstmts;
	public CallableStatementImpl(Connection con, CallableStatement cstmt, String sql) {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.cstmt = cstmt;
		this.cstmts = null;
	}
	public CallableStatementImpl(Connection con, CallableStatement[] cstmts, String sql) {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.cstmts = cstmts;
	}
	public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(parameterIndex, sqlType);
			}
		} else {
			this.cstmt.registerOutParameter(parameterIndex, sqlType);
		}
	}
	public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(parameterIndex, sqlType, scale);
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
/**
 * @deprecated      
 */
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
	public Object getObject(int i, Map map) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(i, map);
		} else {
			return this.cstmt.getObject(i, map);
		}
	}
	public Ref getRef(int i) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getRef(i);
		} else {
			return this.cstmt.getRef(i);
		}
	}
	public Blob getBlob(int i) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBlob(i);
		} else {
			return this.cstmt.getBlob(i);
		}
	}
	public Clob getClob(int i) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getClob(i);
		} else {
			return this.cstmt.getClob(i);
		}
	}
	public Array getArray(int i) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getArray(i);
		} else {
			return this.cstmt.getArray(i);
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
	public void registerOutParameter(int paramIndex, int sqlType, String typeName)throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(paramIndex, sqlType, typeName);
			}
		} else {
			this.cstmt.registerOutParameter(paramIndex, sqlType, typeName);
		}
	}
	public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(parameterName, sqlType);
			}
		} else {
			this.cstmt.registerOutParameter(parameterName, sqlType);
		}
	}
	public void registerOutParameter(String parameterName, int sqlType, int scale)throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(parameterName, sqlType, scale);
			}
		} else {
			this.cstmt.registerOutParameter(parameterName, sqlType, scale);
		}
	}
	public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(parameterName, sqlType, typeName);
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
	public void setURL(String parameterIndex, URL x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setURL(parameterIndex, x);
			}
		} else {
			this.cstmt.setURL(parameterIndex, x);
		}
	}
	public void setNull(String parameterName, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNull(parameterName, sqlType);
			}
		} else {
			this.cstmt.setNull(parameterName, sqlType);
		}
	}
	public void setBoolean(String parameterName, boolean x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBoolean(parameterName, x);
			}
		} else {
			this.cstmt.setBoolean(parameterName, x);
		}
	}
	public void setByte(String parameterName, byte x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setByte(parameterName, x);
			}
		} else {
			this.cstmt.setByte(parameterName, x);
		}
	}
	public void setShort(String parameterName, short x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setShort(parameterName, x);
			}
		} else {
			this.cstmt.setShort(parameterName, x);
		}
	}
	public void setInt(String parameterName, int x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setInt(parameterName, x);
			}
		} else {
			this.cstmt.setInt(parameterName, x);
		}
	}
	public void setLong(String parameterName, long x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setLong(parameterName, x);
			}
		} else {
			this.cstmt.setLong(parameterName, x);
		}
	}
	public void setFloat(String parameterName, float x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setFloat(parameterName, x);
			}
		} else {
			this.cstmt.setFloat(parameterName, x);
		}
	}
	public void setDouble(String parameterName, double x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDouble(parameterName, x);
			}
		} else {
			this.cstmt.setDouble(parameterName, x);
		}
	}
	public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBigDecimal(parameterName, x);
			}
		} else {
			this.cstmt.setBigDecimal(parameterName, x);
		}
	}
	public void setString(String parameterName, String x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setString(parameterName, x);
			}
		} else {
			this.cstmt.setString(parameterName, x);
		}
	}
	public void setBytes(String parameterName, byte[] x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBytes(parameterName, x);
			}
		} else {
			this.cstmt.setBytes(parameterName, x);
		}
	}
	public void setDate(String parameterName, Date x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDate(parameterName, x);
			}
		} else {
			this.cstmt.setDate(parameterName, x);
		}
	}
	public void setTime(String parameterName, Time x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTime(parameterName, x);
			}
		} else {
			this.cstmt.setTime(parameterName, x);
		}
	}
	public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTimestamp(parameterName, x);
			}
		} else {
			this.cstmt.setTimestamp(parameterName, x);
		}
	}
	public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setAsciiStream(parameterName, x, length);
			}
		} else {
			this.cstmt.setAsciiStream(parameterName, x, length);
		}
	}
	public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBinaryStream(parameterName, x, length);
			}
		} else {
			this.cstmt.setBinaryStream(parameterName, x, length);
		}
	}
	public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(parameterName, x, targetSqlType, scale);
			}
		} else {
			this.cstmt.setObject(parameterName, x, targetSqlType, scale);
		}
	}
	public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(parameterName, x, targetSqlType);
			}
		} else {
			this.cstmt.setObject(parameterName, x, targetSqlType);
		}
	}
	public void setObject(String parameterName, Object x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(parameterName, x);
			}
		} else {
			this.cstmt.setObject(parameterName, x);
		}
	}
	public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setCharacterStream(parameterName, reader, length);
			}
		} else {
			this.cstmt.setCharacterStream(parameterName, reader, length);
		}
	}
	public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDate(parameterName, x, cal);
			}
		} else {
			this.cstmt.setDate(parameterName, x, cal);
		}
	}
	public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTime(parameterName, x, cal);
			}
		} else {
			this.cstmt.setTime(parameterName, x, cal);
		}
	}
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTimestamp(parameterName, x, cal);
			}
		} else {
			this.cstmt.setTimestamp(parameterName, x, cal);
		}
	}
	public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNull(parameterName, sqlType, typeName);
			}
		} else {
			this.cstmt.setNull(parameterName, sqlType, typeName);
		}
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
	public Object getObject(String parameterName, Map map) throws SQLException {
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
	public java.net.URL getURL(String parameterName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getURL(parameterName);
		} else {
			return this.cstmt.getURL(parameterName);
		}
	}
	
	
	public void addBatch() throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].addBatch();
			}
		} else {
			this.cstmt.addBatch();
		}
	}

	public void clearParameters()throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].clearParameters();
			}
		} else {
			this.cstmt.clearParameters();
		}
	}

	public boolean execute() throws java.sql.SQLException {
		if(this.cstmts != null) {
			boolean b = false;
			for(int i = 0; i < this.cstmts.length; i++) {
				b = cstmts[i].execute();
			}
			return b;
		} else {
			return this.cstmt.execute();
		}
	}

	public ResultSet executeQuery() throws java.sql.SQLException {
		if(this.cstmts != null) {
			return new ResultSetImpl(this, this.cstmts[0].executeQuery());
		} else {
			return new ResultSetImpl(this, this.cstmt.executeQuery());
		}
	}

	public int executeUpdate() throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(i == this.cstmts.length - 1) {
					return cstmts[i].executeUpdate();
				} else {
					cstmts[i].executeUpdate();
				}
			}
			return 0;
		} else {
			return this.cstmt.executeUpdate();
		}
	}

	public ResultSetMetaData getMetaData() throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getMetaData();
		} else {
			return this.cstmt.getMetaData();
		}
	}

	public ParameterMetaData getParameterMetaData() throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getParameterMetaData();
		} else {
			return this.cstmt.getParameterMetaData();
		}
	}

	public void setArray(int i,Array  x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int y = 0; y < this.cstmts.length; y++) {
				cstmts[y].setArray(i, x);
			}
		} else {
			this.cstmt.setArray(i, x);
		}
	}

	public void setAsciiStream(int parameterIndex,InputStream  x,int length) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setAsciiStream(parameterIndex, x, length);
			}
		} else {
			this.cstmt.setAsciiStream(parameterIndex, x, length);
		}
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBigDecimal(parameterIndex, x);
			}
		} else {
			this.cstmt.setBigDecimal(parameterIndex, x);
		}
	}
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBinaryStream(parameterIndex, x, length);
			}
		} else {
			this.cstmt.setBinaryStream(parameterIndex, x, length);
		}
	}
	public void setBlob(int i, Blob x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int y = 0; y < this.cstmts.length; y++) {
				cstmts[y].setBlob(i, x);
			}
		} else {
			this.cstmt.setBlob(i, x);
		}
	}
	public void setBoolean(int parameterIndex, boolean x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBoolean(parameterIndex, x);
			}
		} else {
			this.cstmt.setBoolean(parameterIndex, x);
		}
	}
	public void setByte(int parameterIndex, byte x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setByte(parameterIndex, x);
			}
		} else {
			this.cstmt.setByte(parameterIndex, x);
		}
	}
	public void setBytes(int parameterIndex, byte[] x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBytes(parameterIndex, x);
			}
		} else {
			this.cstmt.setBytes(parameterIndex, x);
		}
	}
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setCharacterStream(parameterIndex, reader, length);
			}
		} else {
			this.cstmt.setCharacterStream(parameterIndex, reader, length);
		}
	}
	public void setClob(int i, Clob x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int y = 0; y < this.cstmts.length; y++) {
				cstmts[y].setClob(i, x);
			}
		} else {
			this.cstmt.setClob(i, x);
		}
	}
	public void setDate(int parameterIndex, Date x, Calendar cal) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDate(parameterIndex, x, cal);
			}
		} else {
			this.cstmt.setDate(parameterIndex, x, cal);
		}
	}
	public void setDate(int parameterIndex, Date x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDate(parameterIndex, x);
			}
		} else {
			this.cstmt.setDate(parameterIndex, x);
		}
	}
	public void setDouble(int parameterIndex, double x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDouble(parameterIndex, x);
			}
		} else {
			this.cstmt.setDouble(parameterIndex, x);
		}
	}
	public void setFloat(int parameterIndex, float x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setFloat(parameterIndex, x);
			}
		} else {
			this.cstmt.setFloat(parameterIndex, x);
		}
	}
	public void setInt(int parameterIndex, int x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setInt(parameterIndex, x);
			}
		} else {
			this.cstmt.setInt(parameterIndex, x);
		}
	}
	public void setLong(int parameterIndex, long x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setLong(parameterIndex, x);
			}
		} else {
			this.cstmt.setLong(parameterIndex, x);
		}
	}
	public void setNull(int paramIndex, int sqlType, String typeName) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNull(paramIndex, sqlType, typeName);
			}
		} else {
			this.cstmt.setNull(paramIndex, sqlType, typeName);
		}
	}
	public void setNull(int parameterIndex, int sqlType) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNull(parameterIndex, sqlType);
			}
		} else {
			this.cstmt.setNull(parameterIndex, sqlType);
		}
	}
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(parameterIndex, x, targetSqlType, scale);
			}
		} else {
			this.cstmt.setObject(parameterIndex, x, targetSqlType, scale);
		}
	}
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(parameterIndex, x, targetSqlType);
			}
		} else {
			this.cstmt.setObject(parameterIndex, x, targetSqlType);
		}
	}
	public void setObject(int parameterIndex, Object x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(parameterIndex, x);
			}
		} else {
			this.cstmt.setObject(parameterIndex, x);
		}
	}
	public void setRef(int i, Ref x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int y = 0; y < this.cstmts.length; y++) {
				cstmts[y].setRef(i, x);
			}
		} else {
			this.cstmt.setRef(i, x);
		}
	}
	public void setShort(int parameterIndex, short x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setShort(parameterIndex, x);
			}
		} else {
			this.cstmt.setShort(parameterIndex, x);
		}
	}
	public void setString(int parameterIndex, String x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setString(parameterIndex, x);
			}
		} else {
			this.cstmt.setString(parameterIndex, x);
		}
	}
	public void setTime(int parameterIndex, Time x, Calendar cal) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTime(parameterIndex, x, cal);
			}
		} else {
			this.cstmt.setTime(parameterIndex, x, cal);
		}
	}
	public void setTime(int parameterIndex, Time x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTime(parameterIndex, x);
			}
		} else {
			this.cstmt.setTime(parameterIndex, x);
		}
	}
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTimestamp(parameterIndex, x, cal);
			}
		} else {
			this.cstmt.setTimestamp(parameterIndex, x, cal);
		}
	}
	public void setTimestamp(int parameterIndex, Timestamp x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTimestamp(parameterIndex, x);
			}
		} else {
			this.cstmt.setTimestamp(parameterIndex, x);
		}

	}
/**
 * @deprecated      
 */
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setUnicodeStream(parameterIndex, x, length);
			}
		} else {
			this.cstmt.setUnicodeStream(parameterIndex, x, length);
		}

	}
	public void setURL(int parameterIndex, URL x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setURL(parameterIndex, x);
			}
		} else {
			this.cstmt.setURL(parameterIndex, x);
		}
	}
	public ResultSet executeQuery(String sql) throws SQLException {
		if(this.cstmts != null) {
			return new ResultSetImpl(this, this.cstmts[0].executeQuery(sql));
		} else {
			
			return new ResultSetImpl(this, this.cstmt.executeQuery(sql));
		}
	}
	public int executeUpdate(String sql) throws SQLException {
		if(this.cstmts != null) {
			int r = 0;
			for(int i = 0; i < this.cstmts.length; i++) {
				r = cstmts[i].executeUpdate(sql);
			}
			return r;
		} else {
			return this.cstmt.executeUpdate(sql);
		}
	}
	public void close() throws SQLException {
		ConnectionManager.getInstance().clear(this.index);
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].close();
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
		this.cstmt.setMaxFieldSize(max);
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
				cstmts[i].setMaxRows(max);
			}
			
		} else {
			this.cstmt.setMaxRows(max);
		}
	}
	public void setEscapeProcessing(boolean enable) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setEscapeProcessing(enable);
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
				cstmts[i].setQueryTimeout(seconds);
			}
			
		} else {
			this.cstmt.setQueryTimeout(seconds);
		}
	}
	public void cancel() throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].cancel();
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
				cstmts[i].clearWarnings();
			}
			
		} else {
			this.cstmt.clearWarnings();
		}
	}
	public void setCursorName(String name) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setCursorName(name);
			}
			
		} else {
			this.cstmt.setCursorName(name);
		}
	}
	public boolean execute(String sql) throws SQLException {
		if(this.cstmts != null) {
			boolean r = false;
			for(int i = 0; i < this.cstmts.length; i++) {
				r = cstmts[i].execute(sql);
			}
			return r;
		} else {
			return this.cstmt.execute(sql);
		}
	}
	public ResultSet getResultSet() throws SQLException {
		if(this.cstmts != null) {
		return new ResultSetImpl(this, this.cstmts[0].getResultSet());
		} else {
			return new ResultSetImpl(this, this.cstmt.getResultSet());
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
				cstmts[i].setFetchDirection(direction);
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
				cstmts[i].setFetchSize(rows);
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
	public void addBatch(String sql) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].addBatch(sql);
			}
		} else {
		this.cstmt.addBatch(sql);
		}
	}
	public void clearBatch() throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].clearBatch();
			}
		} else {
		this.cstmt.clearBatch();
		}
	}
	public int[] executeBatch() throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(i == this.cstmts.length - 1) {
					return cstmts[i].executeBatch();
				} else {
					cstmts[i].executeBatch();
				}
			}
			return null;
		} else {
		return this.cstmt.executeBatch();
		}
	}
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
			return new ResultSetImpl(this, this.cstmts[0].getGeneratedKeys());
		} else {
			return new ResultSetImpl(this, this.cstmt.getGeneratedKeys());
		}
	}
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(i == this.cstmts.length - 1) {
					return cstmts[i].executeUpdate(sql, autoGeneratedKeys);
				} else {
					cstmts[i].executeUpdate(sql, autoGeneratedKeys);
				}
			}
			return 0;
		} else {
			return this.cstmt.executeUpdate(sql, autoGeneratedKeys);
		}
	}
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(i == this.cstmts.length - 1) {
					return cstmts[i].executeUpdate(sql, columnIndexes);
				} else {
					cstmts[i].executeUpdate(sql, columnIndexes);
				}
			}
			return 0;
		} else {
			return this.cstmt.executeUpdate(sql, columnIndexes);
		}
	}
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(i == this.cstmts.length - 1) {
					return cstmts[i].executeUpdate(sql, columnNames);
				} else {
					cstmts[i].executeUpdate(sql, columnNames);
				}
			}
			return 0;
		} else {
			return this.cstmt.executeUpdate(sql, columnNames);
		}
	}
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(i == this.cstmts.length - 1) {
					return cstmts[i].execute(sql, autoGeneratedKeys);
				} else {
					cstmts[i].execute(sql, autoGeneratedKeys);
				}
			}
			return false;
		} else {
			return this.cstmt.execute(sql, autoGeneratedKeys);
		}
	}
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(i == this.cstmts.length - 1) {
					return cstmts[i].execute(sql, columnIndexes);
				} else {
					cstmts[i].execute(sql, columnIndexes);
				}
			}
			return false;
		} else {
			return this.cstmt.execute(sql, columnIndexes);
		}
	}
	public boolean execute(String sql, String[] columnNames)throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(i == this.cstmts.length - 1) {
					return cstmts[i].execute(sql, columnNames);
				} else {
					cstmts[i].execute(sql, columnNames);
				}
			}
			return false;
		} else {
			return this.cstmt.execute(sql, columnNames);
		}
	}
	public int getResultSetHoldability() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getResultSetHoldability();
		} else {
			return this.cstmt.getResultSetHoldability();
		}
	}
	public java.io.Reader getCharacterStream(int arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getCharacterStream(arg0);
		} else {
			return this.cstmt.getCharacterStream(arg0);
		}
	}
	public java.io.Reader getCharacterStream(java.lang.String arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getCharacterStream(arg0);
		} else {
			return this.cstmt.getCharacterStream(arg0);
		}
	}
	public java.io.Reader getNCharacterStream(int arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNCharacterStream(arg0);
		} else {
			return this.cstmt.getNCharacterStream(arg0);
		}
	}
	public java.io.Reader getNCharacterStream(java.lang.String arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNCharacterStream(arg0);
		} else {
			return this.cstmt.getNCharacterStream(arg0);
		}
	}
	public java.sql.NClob getNClob(int arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNClob(arg0);
		} else {
			return this.cstmt.getNClob(arg0);
		}
	}
	public java.sql.NClob getNClob(java.lang.String arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNClob(arg0);
		} else {
			return this.cstmt.getNClob(arg0);
		}
	}
	public java.lang.String getNString(int arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNString(arg0);
		} else {
			return this.cstmt.getNString(arg0);
		}
	}
	public java.lang.String getNString(java.lang.String arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getNString(arg0);
		} else {
			return this.cstmt.getNString(arg0);
		}
	}
	public java.lang.Object getObject(int arg0, java.lang.Class arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(arg0, arg1);
		} else {
			return this.cstmt.getObject(arg0, arg1);
		}
	}
	public java.lang.Object getObject(java.lang.String arg0, java.lang.Class arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(arg0, arg1);
		} else {
			return this.cstmt.getObject(arg0, arg1);
		}
	}
	public java.sql.RowId getRowId(int arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getRowId(arg0);
		} else {
			return this.cstmt.getRowId(arg0);
		}
	}
	public java.sql.RowId getRowId(java.lang.String arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getRowId(arg0);
		} else {
			return this.cstmt.getRowId(arg0);
		}
	}
	public java.sql.SQLXML getSQLXML(int arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getSQLXML(arg0);
		} else {
			return this.cstmt.getSQLXML(arg0);
		}
	}
	public java.sql.SQLXML getSQLXML(java.lang.String arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getSQLXML(arg0);
		} else {
			return this.cstmt.getSQLXML(arg0);
		}
	}
	public void setAsciiStream(java.lang.String arg0, java.io.InputStream arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setAsciiStream(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setAsciiStream(arg0, arg1, arg2);
		}
	}
	public void setAsciiStream(java.lang.String arg0, java.io.InputStream arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setAsciiStream(arg0, arg1);
			}
		} else {
			this.cstmt.setAsciiStream(arg0, arg1);
		}
	}
	public void setBinaryStream(java.lang.String arg0, java.io.InputStream arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBinaryStream(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setBinaryStream(arg0, arg1, arg2);
		}
	}
	public void setBinaryStream(java.lang.String arg0, java.io.InputStream arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBinaryStream(arg0, arg1);
			}
		} else {
			this.cstmt.setBinaryStream(arg0, arg1);
		}
	}
	public void setBlob(java.lang.String arg0, java.sql.Blob arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBlob(arg0, arg1);
			}
		} else {
			this.cstmt.setBlob(arg0, arg1);
		}
	}
	public void setBlob(java.lang.String arg0, java.io.InputStream arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBlob(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setBlob(arg0, arg1, arg2);
		}
	}
	public void setBlob(java.lang.String arg0, java.io.InputStream arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBlob(arg0, arg1);
			}
		} else {
			this.cstmt.setBlob(arg0, arg1);
		}
	}
	public void setCharacterStream(java.lang.String arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setCharacterStream(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setCharacterStream(arg0, arg1, arg2);
		}
	}
	public void setCharacterStream(java.lang.String arg0, java.io.Reader arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setCharacterStream(arg0, arg1);
			}
		} else {
			this.cstmt.setCharacterStream(arg0, arg1);
		}
	}
	public void setClob(java.lang.String arg0, java.sql.Clob arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setClob(arg0, arg1);
			}
		} else {
			this.cstmt.setClob(arg0, arg1);
		}
	}
	public void setClob(java.lang.String arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setClob(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setClob(arg0, arg1, arg2);
		}
	}
	public void setClob(java.lang.String arg0, java.io.Reader arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setClob(arg0, arg1);
			}
		} else {
			this.cstmt.setClob(arg0, arg1);
		}
	}
	public void setNCharacterStream(java.lang.String arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNCharacterStream(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setNCharacterStream(arg0, arg1, arg2);
		}
	}
	public void setNCharacterStream(java.lang.String arg0, java.io.Reader arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNCharacterStream(arg0, arg1);
			}
		} else {
			this.cstmt.setNCharacterStream(arg0, arg1);
		}
	}
	public void setNClob(java.lang.String arg0, java.sql.NClob arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNClob(arg0, arg1);
			}
		} else {
			this.cstmt.setNClob(arg0, arg1);
		}
	}
	public void setNClob(java.lang.String arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNClob(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setNClob(arg0, arg1, arg2);
		}
	}
	public void setNClob(java.lang.String arg0, java.io.Reader arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNClob(arg0, arg1);
			}
		} else {
			this.cstmt.setNClob(arg0, arg1);
		}
	}
	public void setNString(java.lang.String arg0, java.lang.String arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNString(arg0, arg1);
			}
		} else {
			this.cstmt.setNString(arg0, arg1);
		}
	}
	public void setRowId(java.lang.String arg0, java.sql.RowId arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setRowId(arg0, arg1);
			}
		} else {
			this.cstmt.setRowId(arg0, arg1);
		}
	}
	public void setSQLXML(java.lang.String arg0, java.sql.SQLXML arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setSQLXML(arg0, arg1);
			}
		} else {
			this.cstmt.setSQLXML(arg0, arg1);
		}
	}

	
	public void setAsciiStream(int arg0, InputStream arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setAsciiStream(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setAsciiStream(arg0, arg1, arg2);
		}
	}
	public void setAsciiStream(int arg0, InputStream arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setAsciiStream(arg0, arg1);
			}
		} else {
			this.cstmt.setAsciiStream(arg0, arg1);
		}
	}
	public void setBinaryStream(int arg0, InputStream arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBinaryStream(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setBinaryStream(arg0, arg1, arg2);
		}
	}
	public void setBinaryStream(int arg0, InputStream arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBinaryStream(arg0, arg1);
			}
		} else {
			this.cstmt.setBinaryStream(arg0, arg1);
		}
	}
	public void setBlob(int arg0, InputStream arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBlob(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setBlob(arg0, arg1, arg2);
		}
	}
	public void setBlob(int arg0, InputStream arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBlob(arg0, arg1);
			}
		} else {
			this.cstmt.setBlob(arg0, arg1);
		}
	}
	public void setCharacterStream(int arg0, Reader arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setCharacterStream(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setCharacterStream(arg0, arg1, arg2);
		}
	}
	public void setCharacterStream(int arg0, Reader arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setCharacterStream(arg0, arg1);
			}
		} else {
			this.cstmt.setCharacterStream(arg0, arg1);
		}
	}
	public void setClob(int arg0, Reader arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setClob(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setClob(arg0, arg1, arg2);
		}
	}
	public void setClob(int arg0, Reader arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setClob(arg0, arg1);
			}
		} else {
			this.cstmt.setClob(arg0, arg1);
		}
	}
	public void setNCharacterStream(int arg0, Reader arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNCharacterStream(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setNCharacterStream(arg0, arg1, arg2);
		}
	}
	public void setNCharacterStream(int arg0, Reader arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNCharacterStream(arg0, arg1);
			}
		} else {
			this.cstmt.setNCharacterStream(arg0, arg1);
		}
	}
	public void setNClob(int arg0, java.sql.NClob arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNClob(arg0, arg1);
			}
		} else {
			this.cstmt.setNClob(arg0, arg1);
		}
	}
	public void setNClob(int arg0, Reader arg1, long arg2) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNClob(arg0, arg1, arg2);
			}
		} else {
			this.cstmt.setNClob(arg0, arg1, arg2);
		}
	}
	public void setNClob(int arg0, Reader arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNClob(arg0, arg1);
			}
		} else {
			this.cstmt.setNClob(arg0, arg1);
		}
	}
	public void setNString(int arg0, String arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNString(arg0, arg1);
			}
		} else {
			this.cstmt.setNString(arg0, arg1);
		}
	}
	public void setRowId(int arg0, java.sql.RowId arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setRowId(arg0, arg1);
			}
		} else {
			this.cstmt.setRowId(arg0, arg1);
		}
	}
	public void setSQLXML(int arg0, java.sql.SQLXML arg1) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setSQLXML(arg0, arg1);
			}
		} else {
			this.cstmt.setSQLXML(arg0, arg1);
		}
	}
	
	public void closeOnCompletion() throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].closeOnCompletion();
			}
		} else {
			this.cstmt.closeOnCompletion();
		}
	}
	public boolean isClosed() throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].isClosed();
		} else {
			return this.cstmt.isClosed();
		}
	}
	public boolean isCloseOnCompletion() throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].isCloseOnCompletion();
		} else {
			return this.cstmt.isCloseOnCompletion();
		}
	}
	public boolean isPoolable() throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].isPoolable();
		} else {
			return this.cstmt.isPoolable();
		}
	}
	public void setPoolable(boolean arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setPoolable(arg0);
			}
		} else {
		this.cstmt.setPoolable(arg0);
		}
	}
	public java.lang.Object unwrap(java.lang.Class arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				if(i == this.cstmts.length - 1) {
					return cstmts[i].unwrap(arg0);
				} else {
					cstmts[i].unwrap(arg0);
				}
			}
			return null;
		} else {
			return this.cstmt.unwrap(arg0);
		}
	}
	public boolean isWrapperFor(java.lang.Class arg0) throws java.sql.SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].isWrapperFor(arg0);
		} else {
			return this.cstmt.isWrapperFor(arg0);
		}
	}
	
}
