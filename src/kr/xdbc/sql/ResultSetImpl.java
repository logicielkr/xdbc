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

import java.sql.SQLException;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.io.InputStream;
import java.sql.SQLWarning;
import java.sql.ResultSetMetaData;
import java.io.Reader;
import java.sql.Statement;
import java.sql.Ref;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Array;
import java.util.Calendar;
import java.net.URL;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import kr.xdbc.trace.ConnectionManager;
/**
 * ResultSet Wrapper

 * @author HeonJik, KIM
 * @version 0.2
 * @since 0.1
 */
public class ResultSetImpl implements ResultSet {
	private int index;
	private ResultSet rs;
	private Statement pstmt;
	public ResultSetImpl(Statement pstmt, ResultSet rs) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.pstmt = pstmt;
		this.rs = rs;
	}
	public ResultSetImpl(PreparedStatement pstmt, ResultSet rs) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.pstmt = pstmt;
		this.rs = rs;
	}
	public ResultSetImpl(CallableStatement pstmt, ResultSet rs) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.pstmt = pstmt;
		this.rs = rs;
	}
	public boolean next() throws SQLException {
		return this.rs.next();
	}
	public void close() throws SQLException {
		ConnectionManager.getInstance().clear(this.index);
		this.rs.close();
	}
	public boolean wasNull() throws SQLException {
		return this.rs.wasNull();
	}
	public String getString(int columnIndex) throws SQLException {
		return this.rs.getString(columnIndex);
	}
	public boolean getBoolean(int columnIndex) throws SQLException {
		return this.rs.getBoolean(columnIndex);
	}
	public byte getByte(int columnIndex) throws SQLException {
		return this.rs.getByte(columnIndex);
	}
	public short getShort(int columnIndex) throws SQLException {
		return this.rs.getShort(columnIndex);
	}
	public int getInt(int columnIndex) throws SQLException {
		return this.rs.getInt(columnIndex);
	}
	public long getLong(int columnIndex) throws SQLException {
		return this.rs.getLong(columnIndex);
	}
	public float getFloat(int columnIndex) throws SQLException {
		return this.rs.getFloat(columnIndex);
	}
	public double getDouble(int columnIndex) throws SQLException {
		return this.rs.getDouble(columnIndex);
	}
/**
 * @deprecated      
 */
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		return this.rs.getBigDecimal(columnIndex, scale);
	}
	public byte[] getBytes(int columnIndex) throws SQLException {
		return this.rs.getBytes(columnIndex);
	}
	public Date getDate(int columnIndex) throws SQLException {
		return this.rs.getDate(columnIndex);
	}
	public Time getTime(int columnIndex) throws SQLException {
		return this.rs.getTime(columnIndex);
	}
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return this.rs.getTimestamp(columnIndex);
	}
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return this.rs.getAsciiStream(columnIndex);
	}
/**
 * @deprecated      
 */
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return this.rs.getUnicodeStream(columnIndex);
	}
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return this.rs.getBinaryStream(columnIndex);
	}
	public String getString(String columnName) throws SQLException {
		return this.rs.getString(columnName);
	}
	public boolean getBoolean(String columnName) throws SQLException {
		return this.rs.getBoolean(columnName);
	}
	public byte getByte(String columnName) throws SQLException {
		return this.rs.getByte(columnName);
	}
	public short getShort(String columnName) throws SQLException {
		return this.rs.getShort(columnName);
	}
	public int getInt(String columnName) throws SQLException {
		return this.rs.getInt(columnName);
	}
	public long getLong(String columnName) throws SQLException {
		return this.rs.getLong(columnName);
	}
	public float getFloat(String columnName) throws SQLException {
		return this.rs.getFloat(columnName);
	}
	public double getDouble(String columnName) throws SQLException {
		return this.rs.getDouble(columnName);
	}
/**
 * @deprecated      
 */
	public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
		return this.rs.getBigDecimal(columnName, scale);
	}
	public byte[] getBytes(String columnName) throws SQLException {
		return this.rs.getBytes(columnName);
	}
	public Date getDate(String columnName) throws SQLException {
		return this.rs.getDate(columnName);
	}
	public Time getTime(String columnName) throws SQLException {
		return this.rs.getTime(columnName);
	}
	public Timestamp getTimestamp(String columnName) throws SQLException {
		return this.rs.getTimestamp(columnName);
	}
	public InputStream getAsciiStream(String columnName) throws SQLException {
		return this.rs.getAsciiStream(columnName);
	}
/**
 * @deprecated      
 */
	public InputStream getUnicodeStream(String columnName) throws SQLException {
		return this.rs.getUnicodeStream(columnName);
	}
	public InputStream getBinaryStream(String columnName)throws SQLException {
		return this.rs.getBinaryStream(columnName);
	}
	public SQLWarning getWarnings() throws SQLException {
		return this.rs.getWarnings();
	}
	public void clearWarnings() throws SQLException {
		this.rs.clearWarnings();
	}
	public String getCursorName() throws SQLException {
		return this.rs.getCursorName();
	}
	public ResultSetMetaData getMetaData() throws SQLException {
		return this.rs.getMetaData();
	}
	public Object getObject(int columnIndex) throws SQLException {
		return this.rs.getObject(columnIndex);
	}
	public Object getObject(String columnName) throws SQLException {
		return this.rs.getObject(columnName);
	}
	public int findColumn(String columnName) throws SQLException {
		return this.rs.findColumn(columnName);
	}
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return this.rs.getCharacterStream(columnIndex);
	}
	public Reader getCharacterStream(String columnName) throws SQLException {
		return this.rs.getCharacterStream(columnName);
	}
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return this.rs.getBigDecimal(columnIndex);
	}
	public BigDecimal getBigDecimal(String columnName) throws SQLException {
		return this.rs.getBigDecimal(columnName);
	}
	public boolean isBeforeFirst() throws SQLException {
		return this.rs.isBeforeFirst();
	}
	public boolean isAfterLast() throws SQLException {
		return this.rs.isAfterLast();
	}
	public boolean isFirst() throws SQLException {
		return this.rs.isFirst();
	}
	public boolean isLast() throws SQLException {
		return this.rs.isLast();
	}
	public void beforeFirst() throws SQLException {
		this.rs.beforeFirst();
	}
	public void afterLast() throws SQLException {
		this.rs.afterLast();
	}
	public boolean first() throws SQLException {
		return this.rs.first();
	}
	public boolean last() throws SQLException {
		return this.rs.last();
	}
	public int getRow() throws SQLException {
		return this.rs.getRow();
	}
	public boolean absolute(int row) throws SQLException {
		return this.rs.absolute(row);
	}
	public boolean relative(int rows) throws SQLException {
		return this.rs.relative(rows);
	}
	public boolean previous() throws SQLException {
		return this.rs.previous();
	}
	public void setFetchDirection(int direction) throws SQLException {
		this.rs.setFetchDirection(direction);
	}
	public int getFetchDirection() throws SQLException {
		return this.rs.getFetchDirection();
	}
	public void setFetchSize(int rows) throws SQLException {
		this.rs.setFetchSize(rows);
	}
	public int getFetchSize() throws SQLException {
		return this.rs.getFetchSize();
	}
	public int getType() throws SQLException {
		return this.rs.getType();
	}
	public int getConcurrency() throws SQLException {
		return this.rs.getConcurrency();
	}
	public boolean rowUpdated() throws SQLException {
		return this.rs.rowUpdated();
	}
	public boolean rowInserted() throws SQLException {
		return this.rs.rowInserted();
	}
	public boolean rowDeleted() throws SQLException {
		return this.rs.rowDeleted();
	}
	public void updateNull(int columnIndex) throws SQLException {
		this.rs.updateNull(columnIndex);
	}
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		this.rs.updateBoolean(columnIndex, x);
	}
	public void updateByte(int columnIndex, byte x) throws SQLException {
		this.rs.updateByte(columnIndex, x);
	}
	public void updateShort(int columnIndex, short x) throws SQLException {
		this.rs.updateShort(columnIndex, x);
	}
	public void updateInt(int columnIndex, int x) throws SQLException {
		this.rs.updateInt(columnIndex, x);
	}
	public void updateLong(int columnIndex, long x) throws SQLException {
		this.rs.updateLong(columnIndex, x);
	}
	public void updateFloat(int columnIndex, float x) throws SQLException {
		this.rs.updateFloat(columnIndex, x);
	}
	public void updateDouble(int columnIndex, double x) throws SQLException {
		this.rs.updateDouble(columnIndex, x);
	}
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		this.rs.updateBigDecimal(columnIndex, x);
	}
	public void updateString(int columnIndex, String x) throws SQLException {
		this.rs.updateString(columnIndex, x);
	}
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		this.rs.updateBytes(columnIndex, x);
	}
	public void updateDate(int columnIndex, Date x) throws SQLException {
		this.rs.updateDate(columnIndex, x);
	}
	public void updateTime(int columnIndex, Time x) throws SQLException {
		this.rs.updateTime(columnIndex, x);
	}
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		this.rs.updateTimestamp(columnIndex, x);
	}
	public void updateAsciiStream(int columnIndex, InputStream x, int length)throws SQLException {
		this.rs.updateAsciiStream(columnIndex, x, length);
	}
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		this.rs.updateBinaryStream(columnIndex, x, length);
	}
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		this.rs.updateCharacterStream(columnIndex, x, length);
	}
	public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
		this.rs.updateObject(columnIndex, x, scale);
	}
	public void updateObject(int columnIndex, Object x) throws SQLException {
		this.rs.updateObject(columnIndex, x);
	}
	public void updateNull(String columnName) throws SQLException {
		this.rs.updateNull(columnName);
	}
	public void updateBoolean(String columnName, boolean x) throws SQLException {
		this.rs.updateBoolean(columnName, x);
	}
	public void updateByte(String columnName, byte x) throws SQLException {
		this.rs.updateByte(columnName, x);
	}
	public void updateShort(String columnName, short x) throws SQLException {
		this.rs.updateShort(columnName, x);
	}
	public void updateInt(String columnName, int x) throws SQLException {
		this.rs.updateInt(columnName, x);
	}
	public void updateLong(String columnName, long x) throws SQLException {
		this.rs.updateLong(columnName, x);
	}
	public void updateFloat(String columnName, float x) throws SQLException {
		this.rs.updateFloat(columnName, x);
	}
	public void updateDouble(String columnName, double x) throws SQLException {
		this.rs.updateDouble(columnName, x);
	}
	public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
		this.rs.updateBigDecimal(columnName, x);
	}
	public void updateString(String columnName, String x)throws SQLException {
		this.rs.updateString(columnName, x);
	}
	public void updateBytes(String columnName, byte[] x) throws SQLException {
		this.rs.updateBytes(columnName, x);
	}
	public void updateDate(String columnName, Date x) throws SQLException {
		this.rs.updateDate(columnName, x);
	}
	public void updateTime(String columnName, Time x) throws SQLException {
		this.rs.updateTime(columnName, x);
	}
	public void updateTimestamp(String columnName, Timestamp x) throws SQLException {
		this.rs.updateTimestamp(columnName, x);
	}
	public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
		this.rs.updateAsciiStream(columnName, x, length);
	}
	public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
		this.rs.updateBinaryStream(columnName, x, length);
	}
	public void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
		this.rs.updateCharacterStream(columnName, reader, length);
	}
	public void updateObject(String columnName, Object x, int scale) throws SQLException {
		this.rs.updateObject(columnName, x, scale);
	}
	public void updateObject(String columnName, Object x)throws SQLException {
		this.rs.updateObject(columnName, x);
	}
	public void insertRow() throws SQLException {
		this.rs.insertRow();
	}
	public void updateRow() throws SQLException {
		this.rs.updateRow();
	}
	public void deleteRow() throws SQLException {
		this.rs.deleteRow();
	}
	public void refreshRow() throws SQLException {
		this.rs.refreshRow();
	}
	public void cancelRowUpdates() throws SQLException {
		this.rs.cancelRowUpdates();
	}
	public void moveToInsertRow() throws SQLException {
		this.rs.moveToInsertRow();
	}
	public void moveToCurrentRow() throws SQLException {
		this.rs.moveToCurrentRow();
	}
	public Statement getStatement() throws SQLException {
		return this.rs.getStatement();
	}
	public Object getObject(int i, Map map) throws SQLException {
		return this.rs.getObject(i, map);
	}
	public Ref getRef(int i) throws SQLException {
		return this.rs.getRef(i);
	}
	public Blob getBlob(int i) throws SQLException {
		return this.rs.getBlob(i);
	}
	public Clob getClob(int i) throws SQLException {
		return this.rs.getClob(i);
	}
	public Array getArray(int i) throws SQLException {
		return this.rs.getArray(i);
	}
	public Object getObject(String colName, Map map) throws SQLException {
		return this.rs.getObject(colName, map);
	}
	public Ref getRef(String colName) throws SQLException {
		return this.rs.getRef(colName);
	}
	public Blob getBlob(String colName) throws SQLException {
		return this.rs.getBlob(colName);
	}
	public Clob getClob(String colName) throws SQLException {
		return this.rs.getClob(colName);
	}
	public Array getArray(String colName) throws SQLException {
		return this.rs.getArray(colName);
	}
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return this.rs.getDate(columnIndex, cal);
	}
	public Date getDate(String columnName, Calendar cal) throws SQLException {
		return this.rs.getDate(columnName, cal);
	}
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return this.rs.getTime(columnIndex, cal);
	}
	public Time getTime(String columnName, Calendar cal) throws SQLException {
		return this.rs.getTime(columnName, cal);
	}
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		return this.rs.getTimestamp(columnIndex, cal);
	}
	public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
		return this.rs.getTimestamp(columnName, cal);
	}
	public URL getURL(int columnIndex) throws SQLException {
		return this.rs.getURL(columnIndex);
	}
	public URL getURL(String columnName) throws SQLException {
		return this.rs.getURL(columnName);
	}
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		this.rs.updateRef(columnIndex, x);
	}
	public void updateRef(String columnName, Ref x) throws SQLException {
		this.rs.updateRef(columnName, x);
	}
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		this.rs.updateBlob(columnIndex, x);
	}
	public void updateBlob(String columnName, Blob x) throws SQLException {
		this.rs.updateBlob(columnName, x);
	}
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		this.rs.updateClob(columnIndex, x);
	}
	public void updateClob(String columnName, Clob x) throws SQLException {
		this.rs.updateClob(columnName, x);
	}
	public void updateArray(int columnIndex, Array x) throws SQLException {
		this.rs.updateArray(columnIndex, x);
	}
	public void updateArray(String columnIndex, Array x) throws SQLException {
		this.rs.updateArray(columnIndex, x);
	}
	public ResultSet getResultSet() {
		return this.rs;
	}
	public java.lang.Object unwrap(java.lang.Class arg0) throws java.sql.SQLException {
		return this.rs.unwrap(arg0);
	}
	public boolean isWrapperFor(java.lang.Class arg0) throws java.sql.SQLException {
		return this.rs.isWrapperFor(arg0);
	}
	
	public int getHoldability() throws java.sql.SQLException {
		return this.rs.getHoldability();
	}
	public java.io.Reader getNCharacterStream(int arg0) throws java.sql.SQLException {
		return this.rs.getNCharacterStream(arg0);
	}
	public java.io.Reader getNCharacterStream(java.lang.String arg0) throws java.sql.SQLException {
		return this.rs.getNCharacterStream(arg0);
	}
	public java.sql.NClob getNClob(int arg0) throws java.sql.SQLException {
		return this.rs.getNClob(arg0);
	}
	public java.sql.NClob getNClob(java.lang.String arg0) throws java.sql.SQLException {
		return this.rs.getNClob(arg0);
	}
	public java.lang.String getNString(int arg0) throws java.sql.SQLException {
		return this.rs.getNString(arg0);
	}
	public java.lang.String getNString(java.lang.String arg0) throws java.sql.SQLException {
		return this.rs.getNString(arg0);
	}
	public java.lang.Object getObject(int arg0, java.lang.Class arg1) throws java.sql.SQLException {
		return this.rs.getObject(arg0, arg1);
	}
	public java.lang.Object getObject(java.lang.String arg0, java.lang.Class arg1) throws java.sql.SQLException {
		return this.rs.getObject(arg0, arg1);
	}
	public java.sql.RowId getRowId(int arg0) throws java.sql.SQLException {
		return this.rs.getRowId(arg0);
	}
	public java.sql.RowId getRowId(java.lang.String arg0) throws java.sql.SQLException {
		return this.rs.getRowId(arg0);
	}
	public java.sql.SQLXML getSQLXML(int arg0) throws java.sql.SQLException {
		return this.rs.getSQLXML(arg0);
	}
	public java.sql.SQLXML getSQLXML(java.lang.String arg0) throws java.sql.SQLException {
		return this.rs.getSQLXML(arg0);
	}
	public boolean isClosed() throws java.sql.SQLException {
		return this.rs.isClosed();
	}
	
	
	public void updateAsciiStream(int arg0, java.io.InputStream arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateAsciiStream(arg0, arg1, arg2);
	}
	public void updateAsciiStream(int arg0, java.io.InputStream arg1) throws java.sql.SQLException {
		this.rs.updateAsciiStream(arg0, arg1);
	}
	public void updateAsciiStream(java.lang.String arg0, java.io.InputStream arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateAsciiStream(arg0, arg1, arg2);
	}
	public void updateAsciiStream(java.lang.String arg0, java.io.InputStream arg1) throws java.sql.SQLException {
		this.rs.updateAsciiStream(arg0, arg1);
	}

	public void updateBinaryStream(int arg0, java.io.InputStream arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateBinaryStream(arg0, arg1, arg2);
	}
	public void updateBinaryStream(int arg0, java.io.InputStream arg1) throws java.sql.SQLException {
		this.rs.updateBinaryStream(arg0, arg1);
	}
	public void updateBinaryStream(java.lang.String arg0, java.io.InputStream arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateBinaryStream(arg0, arg1, arg2);
	}
	public void updateBinaryStream(java.lang.String arg0, java.io.InputStream arg1) throws java.sql.SQLException {
		this.rs.updateBinaryStream(arg0, arg1);
	}

	public void updateBlob(int arg0, java.io.InputStream arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateBlob(arg0, arg1, arg2);
	}
	public void updateBlob(int arg0, java.io.InputStream arg1) throws java.sql.SQLException {
		this.rs.updateBlob(arg0, arg1);
	}
	public void updateBlob(java.lang.String arg0, java.io.InputStream arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateBlob(arg0, arg1, arg2);
	}
	public void updateBlob(java.lang.String arg0, java.io.InputStream arg1) throws java.sql.SQLException {
		this.rs.updateBlob(arg0, arg1);
	}
	public void updateCharacterStream(int arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateCharacterStream(arg0, arg1, arg2);
	}
	public void updateCharacterStream(int arg0, java.io.Reader arg1) throws java.sql.SQLException {
		this.rs.updateCharacterStream(arg0, arg1);
	}
	public void updateCharacterStream(java.lang.String arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateCharacterStream(arg0, arg1, arg2);
	}
	public void updateCharacterStream(java.lang.String arg0, java.io.Reader arg1) throws java.sql.SQLException {
		this.rs.updateCharacterStream(arg0, arg1);
	}

	public void updateClob(int arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateClob(arg0, arg1, arg2);
	}
	public void updateClob(int arg0, java.io.Reader arg1) throws java.sql.SQLException {
		this.rs.updateClob(arg0, arg1);
	}
	public void updateClob(java.lang.String arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateClob(arg0, arg1, arg2);
	}
	public void updateClob(java.lang.String arg0, java.io.Reader arg1) throws java.sql.SQLException {
		this.rs.updateClob(arg0, arg1);
	}

	public void updateNCharacterStream(int arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateNCharacterStream(arg0, arg1, arg2);
	}
	public void updateNCharacterStream(int arg0, java.io.Reader arg1) throws java.sql.SQLException {
		this.rs.updateNCharacterStream(arg0, arg1);
	}
	public void updateNCharacterStream(java.lang.String arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateNCharacterStream(arg0, arg1, arg2);
	}
	public void updateNCharacterStream(java.lang.String arg0, java.io.Reader arg1) throws java.sql.SQLException {
		this.rs.updateNCharacterStream(arg0, arg1);
	}

	public void updateNClob(int arg0, java.sql.NClob arg1) throws java.sql.SQLException {
		this.rs.updateNClob(arg0, arg1);
	}
	public void updateNClob(int arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateNClob(arg0, arg1, arg2);
	}
	public void updateNClob(int arg0, java.io.Reader arg1) throws java.sql.SQLException {
		this.rs.updateNClob(arg0, arg1);
	}
	public void updateNClob(java.lang.String arg0, java.sql.NClob arg1) throws java.sql.SQLException {
		this.rs.updateNClob(arg0, arg1);
	}
	public void updateNClob(java.lang.String arg0, java.io.Reader arg1, long arg2) throws java.sql.SQLException {
		this.rs.updateNClob(arg0, arg1, arg2);
	}
	public void updateNClob(java.lang.String arg0, java.io.Reader arg1) throws java.sql.SQLException {
		this.rs.updateNClob(arg0, arg1);
	}

	public void updateNString(int arg0, java.lang.String arg1) throws java.sql.SQLException {
		this.rs.updateNString(arg0, arg1);
	}
	public void updateNString(java.lang.String arg0, java.lang.String arg1) throws java.sql.SQLException {
		this.rs.updateNString(arg0, arg1);
	}

	public void updateRowId(int arg0, java.sql.RowId arg1) throws java.sql.SQLException {
		this.rs.updateRowId(arg0, arg1);
	}
	public void updateRowId(java.lang.String arg0, java.sql.RowId arg1) throws java.sql.SQLException {
		this.rs.updateRowId(arg0, arg1);
	}


	public void updateSQLXML(int arg0, java.sql.SQLXML arg1) throws java.sql.SQLException {
		this.rs.updateSQLXML(arg0, arg1);
	}
	public void updateSQLXML(java.lang.String arg0, java.sql.SQLXML arg1) throws java.sql.SQLException {
		this.rs.updateSQLXML(arg0, arg1);
	}
}

