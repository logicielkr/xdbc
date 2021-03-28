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
	public void registerOutParameter(int pIndex, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(pIndex, sqlType);
			}
		} else {
			this.cstmt.registerOutParameter(pIndex, sqlType);
		}
	}
	public void registerOutParameter(int pIndex, int sqlType, int scale) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(pIndex, sqlType, scale);
			}
		} else {
			this.cstmt.registerOutParameter(pIndex, sqlType, scale);
		}
	}
	public boolean wasNull() throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].wasNull();
		} else {
			return this.cstmt.wasNull();
		}
	}
	public String getString(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getString(pIndex);
		} else {
			return this.cstmt.getString(pIndex);
		}
	}
	public boolean getBoolean(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBoolean(pIndex);
		} else {
			return this.cstmt.getBoolean(pIndex);
		}
	}
	public byte getByte(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getByte(pIndex);
		} else {
			return this.cstmt.getByte(pIndex);
		}
	}
	public short getShort(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getShort(pIndex);
		} else {
			return this.cstmt.getShort(pIndex);
		}
	}
	public int getInt(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getInt(pIndex);
		} else {
			return this.cstmt.getInt(pIndex);
		}
	}
	public long getLong(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getLong(pIndex);
		} else {
			return this.cstmt.getLong(pIndex);
		}
	}
	public float getFloat(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getFloat(pIndex);
		} else {
			return this.cstmt.getFloat(pIndex);
		}
	}
	public double getDouble(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDouble(pIndex);
		} else {
			return this.cstmt.getDouble(pIndex);
		}
	}
/**
 * @deprecated
 */
	public BigDecimal getBigDecimal(int pIndex, int scale) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBigDecimal(pIndex, scale);
		} else {
			return this.cstmt.getBigDecimal(pIndex, scale);
		}
	}
	public byte[] getBytes(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBytes(pIndex);
		} else {
			return this.cstmt.getBytes(pIndex);
		}
	}
	public Date getDate(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDate(pIndex);
		} else {
			return this.cstmt.getDate(pIndex);
		}
	}
	public Time getTime(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTime(pIndex);
		} else {
			return this.cstmt.getTime(pIndex);
		}
	}
	public Timestamp getTimestamp(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTimestamp(pIndex);
		} else {
			return this.cstmt.getTimestamp(pIndex);
		}
	}
	public Object getObject(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(pIndex);
		} else {
			return this.cstmt.getObject(pIndex);
		}
	}
	public BigDecimal getBigDecimal(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBigDecimal(pIndex);
		} else {
			return this.cstmt.getBigDecimal(pIndex);
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
	public Date getDate(int pIndex, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDate(pIndex, cal);
		} else {
			return this.cstmt.getDate(pIndex, cal);
		}

	}
	public Time getTime(int pIndex, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTime(pIndex, cal);
		} else {
			return this.cstmt.getTime(pIndex, cal);
		}
	}
	public Timestamp getTimestamp(int pIndex, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTimestamp(pIndex, cal);
		} else {
			return this.cstmt.getTimestamp(pIndex, cal);
		}

	}
	public void registerOutParameter(int pIndex, int sqlType, String typeName)throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(pIndex, sqlType, typeName);
			}
		} else {
			this.cstmt.registerOutParameter(pIndex, sqlType, typeName);
		}
	}
	public void registerOutParameter(String pName, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(pName, sqlType);
			}
		} else {
			this.cstmt.registerOutParameter(pName, sqlType);
		}
	}
	public void registerOutParameter(String pName, int sqlType, int scale)throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(pName, sqlType, scale);
			}
		} else {
			this.cstmt.registerOutParameter(pName, sqlType, scale);
		}
	}
	public void registerOutParameter(String pName, int sqlType, String typeName) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].registerOutParameter(pName, sqlType, typeName);
			}
		} else {
			this.cstmt.registerOutParameter(pName, sqlType, typeName);
		}
	}
	public URL getURL(int pIndex) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getURL(pIndex);
		} else {
			return this.cstmt.getURL(pIndex);
		}
	}
	public void setURL(String pIndex, URL x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setURL(pIndex, x);
			}
		} else {
			this.cstmt.setURL(pIndex, x);
		}
	}
	public void setNull(String pName, int sqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNull(pName, sqlType);
			}
		} else {
			this.cstmt.setNull(pName, sqlType);
		}
	}
	public void setBoolean(String pName, boolean x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBoolean(pName, x);
			}
		} else {
			this.cstmt.setBoolean(pName, x);
		}
	}
	public void setByte(String pName, byte x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setByte(pName, x);
			}
		} else {
			this.cstmt.setByte(pName, x);
		}
	}
	public void setShort(String pName, short x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setShort(pName, x);
			}
		} else {
			this.cstmt.setShort(pName, x);
		}
	}
	public void setInt(String pName, int x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setInt(pName, x);
			}
		} else {
			this.cstmt.setInt(pName, x);
		}
	}
	public void setLong(String pName, long x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setLong(pName, x);
			}
		} else {
			this.cstmt.setLong(pName, x);
		}
	}
	public void setFloat(String pName, float x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setFloat(pName, x);
			}
		} else {
			this.cstmt.setFloat(pName, x);
		}
	}
	public void setDouble(String pName, double x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDouble(pName, x);
			}
		} else {
			this.cstmt.setDouble(pName, x);
		}
	}
	public void setBigDecimal(String pName, BigDecimal x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBigDecimal(pName, x);
			}
		} else {
			this.cstmt.setBigDecimal(pName, x);
		}
	}
	public void setString(String pName, String x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setString(pName, x);
			}
		} else {
			this.cstmt.setString(pName, x);
		}
	}
	public void setBytes(String pName, byte[] x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBytes(pName, x);
			}
		} else {
			this.cstmt.setBytes(pName, x);
		}
	}
	public void setDate(String pName, Date x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDate(pName, x);
			}
		} else {
			this.cstmt.setDate(pName, x);
		}
	}
	public void setTime(String pName, Time x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTime(pName, x);
			}
		} else {
			this.cstmt.setTime(pName, x);
		}
	}
	public void setTimestamp(String pName, Timestamp x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTimestamp(pName, x);
			}
		} else {
			this.cstmt.setTimestamp(pName, x);
		}
	}
	public void setAsciiStream(String pName, InputStream x, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setAsciiStream(pName, x, length);
			}
		} else {
			this.cstmt.setAsciiStream(pName, x, length);
		}
	}
	public void setBinaryStream(String pName, InputStream x, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBinaryStream(pName, x, length);
			}
		} else {
			this.cstmt.setBinaryStream(pName, x, length);
		}
	}
	public void setObject(String pName, Object x, int targetSqlType, int scale) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(pName, x, targetSqlType, scale);
			}
		} else {
			this.cstmt.setObject(pName, x, targetSqlType, scale);
		}
	}
	public void setObject(String pName, Object x, int targetSqlType) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(pName, x, targetSqlType);
			}
		} else {
			this.cstmt.setObject(pName, x, targetSqlType);
		}
	}
	public void setObject(String pName, Object x) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(pName, x);
			}
		} else {
			this.cstmt.setObject(pName, x);
		}
	}
	public void setCharacterStream(String pName, Reader reader, int length) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setCharacterStream(pName, reader, length);
			}
		} else {
			this.cstmt.setCharacterStream(pName, reader, length);
		}
	}
	public void setDate(String pName, Date x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDate(pName, x, cal);
			}
		} else {
			this.cstmt.setDate(pName, x, cal);
		}
	}
	public void setTime(String pName, Time x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTime(pName, x, cal);
			}
		} else {
			this.cstmt.setTime(pName, x, cal);
		}
	}
	public void setTimestamp(String pName, Timestamp x, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTimestamp(pName, x, cal);
			}
		} else {
			this.cstmt.setTimestamp(pName, x, cal);
		}
	}
	public void setNull(String pName, int sqlType, String typeName) throws SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNull(pName, sqlType, typeName);
			}
		} else {
			this.cstmt.setNull(pName, sqlType, typeName);
		}
	}
	public String getString(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getString(pName);
		} else {
			return this.cstmt.getString(pName);
		}
	}
	public boolean getBoolean(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBoolean(pName);
		} else {
			return this.cstmt.getBoolean(pName);
		}
	}
	public byte getByte(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getByte(pName);
		} else {
			return this.cstmt.getByte(pName);
		}
	}
	public short getShort(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getShort(pName);
		} else {
			return this.cstmt.getShort(pName);
		}
	}
	public int getInt(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getInt(pName);
		} else {
			return this.cstmt.getInt(pName);
		}
	}
	public long getLong(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getLong(pName);
		} else {
			return this.cstmt.getLong(pName);
		}
	}
	public float getFloat(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getFloat(pName);
		} else {
			return this.cstmt.getFloat(pName);
		}
	}
	public double getDouble(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDouble(pName);
		} else {
			return this.cstmt.getDouble(pName);
		}
	}
	public byte[] getBytes(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBytes(pName);
		} else {
			return this.cstmt.getBytes(pName);
		}
	}
	public Date getDate(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDate(pName);
		} else {
			return this.cstmt.getDate(pName);
		}
	}
	public Time getTime(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTime(pName);
		} else {
			return this.cstmt.getTime(pName);
		}
	}
	public Timestamp getTimestamp(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTimestamp(pName);
		} else {
			return this.cstmt.getTimestamp(pName);
		}
	}
	public Object getObject(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(pName);
		} else {
			return this.cstmt.getObject(pName);
		}
	}
	public BigDecimal getBigDecimal(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBigDecimal(pName);
		} else {
			return this.cstmt.getBigDecimal(pName);
		}
	}
	public Object getObject(String pName, Map map) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getObject(pName, map);
		} else {
			return this.cstmt.getObject(pName, map);
		}
	}
	public Ref getRef(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getRef(pName);
		} else {
			return this.cstmt.getRef(pName);
		}
	}
	public Blob getBlob(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getBlob(pName);
		} else {
			return this.cstmt.getBlob(pName);
		}

	}
	public Clob getClob(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getClob(pName);
		} else {
			return this.cstmt.getClob(pName);
		}
	}
	public Array getArray(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getArray(pName);
		} else {
			return this.cstmt.getArray(pName);
		}
	}
	public Date getDate(String pName, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getDate(pName, cal);
		} else {
			return this.cstmt.getDate(pName, cal);
		}
	}
	public Time getTime(String pName, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTime(pName, cal);
		} else {
			return this.cstmt.getTime(pName, cal);
		}
	}
	public Timestamp getTimestamp(String pName, Calendar cal) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getTimestamp(pName, cal);
		} else {
			return this.cstmt.getTimestamp(pName, cal);
		}
	}
	public java.net.URL getURL(String pName) throws SQLException {
		if(this.cstmts != null) {
			return this.cstmts[0].getURL(pName);
		} else {
			return this.cstmt.getURL(pName);
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

	public void setArray(int i,Array x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int y = 0; y < this.cstmts.length; y++) {
				cstmts[y].setArray(i, x);
			}
		} else {
			this.cstmt.setArray(i, x);
		}
	}

	public void setAsciiStream(int pIndex,InputStream x,int length) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setAsciiStream(pIndex, x, length);
			}
		} else {
			this.cstmt.setAsciiStream(pIndex, x, length);
		}
	}

	public void setBigDecimal(int pIndex, BigDecimal x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBigDecimal(pIndex, x);
			}
		} else {
			this.cstmt.setBigDecimal(pIndex, x);
		}
	}
	public void setBinaryStream(int pIndex, InputStream x, int length) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBinaryStream(pIndex, x, length);
			}
		} else {
			this.cstmt.setBinaryStream(pIndex, x, length);
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
	public void setBoolean(int pIndex, boolean x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBoolean(pIndex, x);
			}
		} else {
			this.cstmt.setBoolean(pIndex, x);
		}
	}
	public void setByte(int pIndex, byte x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setByte(pIndex, x);
			}
		} else {
			this.cstmt.setByte(pIndex, x);
		}
	}
	public void setBytes(int pIndex, byte[] x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setBytes(pIndex, x);
			}
		} else {
			this.cstmt.setBytes(pIndex, x);
		}
	}
	public void setCharacterStream(int pIndex, Reader reader, int length) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setCharacterStream(pIndex, reader, length);
			}
		} else {
			this.cstmt.setCharacterStream(pIndex, reader, length);
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
	public void setDate(int pIndex, Date x, Calendar cal) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDate(pIndex, x, cal);
			}
		} else {
			this.cstmt.setDate(pIndex, x, cal);
		}
	}
	public void setDate(int pIndex, Date x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDate(pIndex, x);
			}
		} else {
			this.cstmt.setDate(pIndex, x);
		}
	}
	public void setDouble(int pIndex, double x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setDouble(pIndex, x);
			}
		} else {
			this.cstmt.setDouble(pIndex, x);
		}
	}
	public void setFloat(int pIndex, float x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setFloat(pIndex, x);
			}
		} else {
			this.cstmt.setFloat(pIndex, x);
		}
	}
	public void setInt(int pIndex, int x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setInt(pIndex, x);
			}
		} else {
			this.cstmt.setInt(pIndex, x);
		}
	}
	public void setLong(int pIndex, long x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setLong(pIndex, x);
			}
		} else {
			this.cstmt.setLong(pIndex, x);
		}
	}
	public void setNull(int pIndex, int sqlType, String typeName) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNull(pIndex, sqlType, typeName);
			}
		} else {
			this.cstmt.setNull(pIndex, sqlType, typeName);
		}
	}
	public void setNull(int pIndex, int sqlType) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setNull(pIndex, sqlType);
			}
		} else {
			this.cstmt.setNull(pIndex, sqlType);
		}
	}
	public void setObject(int pIndex, Object x, int targetSqlType, int scale) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(pIndex, x, targetSqlType, scale);
			}
		} else {
			this.cstmt.setObject(pIndex, x, targetSqlType, scale);
		}
	}
	public void setObject(int pIndex, Object x, int targetSqlType) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(pIndex, x, targetSqlType);
			}
		} else {
			this.cstmt.setObject(pIndex, x, targetSqlType);
		}
	}
	public void setObject(int pIndex, Object x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setObject(pIndex, x);
			}
		} else {
			this.cstmt.setObject(pIndex, x);
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
	public void setShort(int pIndex, short x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setShort(pIndex, x);
			}
		} else {
			this.cstmt.setShort(pIndex, x);
		}
	}
	public void setString(int pIndex, String x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setString(pIndex, x);
			}
		} else {
			this.cstmt.setString(pIndex, x);
		}
	}
	public void setTime(int pIndex, Time x, Calendar cal) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTime(pIndex, x, cal);
			}
		} else {
			this.cstmt.setTime(pIndex, x, cal);
		}
	}
	public void setTime(int pIndex, Time x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTime(pIndex, x);
			}
		} else {
			this.cstmt.setTime(pIndex, x);
		}
	}
	public void setTimestamp(int pIndex, Timestamp x, Calendar cal) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTimestamp(pIndex, x, cal);
			}
		} else {
			this.cstmt.setTimestamp(pIndex, x, cal);
		}
	}
	public void setTimestamp(int pIndex, Timestamp x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setTimestamp(pIndex, x);
			}
		} else {
			this.cstmt.setTimestamp(pIndex, x);
		}

	}
/**
 * @deprecated
 */
	public void setUnicodeStream(int pIndex, InputStream x, int length) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setUnicodeStream(pIndex, x, length);
			}
		} else {
			this.cstmt.setUnicodeStream(pIndex, x, length);
		}

	}
	public void setURL(int pIndex, URL x) throws java.sql.SQLException {
		if(this.cstmts != null) {
			for(int i = 0; i < this.cstmts.length; i++) {
				cstmts[i].setURL(pIndex, x);
			}
		} else {
			this.cstmt.setURL(pIndex, x);
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
