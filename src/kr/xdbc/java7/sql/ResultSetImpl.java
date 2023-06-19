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

import kr.xdbc.trace.ConnectionManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.SQLException;
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
import java.sql.SQLXML;
import java.sql.NClob;
import java.sql.RowId;

/**
 * ResultSet Wrapper
 * API reference 로부터 자동으로 생성한 코드가 아닌 경우 javadoc 을 남긴다.
 * Connection 이 여러 개인 경우 ResultSet 에서 데이타를 변경하는 메소드들은 호출하면 안된다. 
 * @author HeonJik, KIM
 * @version 0.3
 * @since 0.3
 */

public class ResultSetImpl implements ResultSet {
/**
 * ConnectionManager에서 관리하는 일련번호
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	private int index;
/**
 * 내부적으로 관리하는 객체
 */
	private ResultSet rs;
/**
 * 내부적으로 관리하는 객체
 */
	private Statement pstmt;
	private boolean multiple = false;
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	protected ResultSetImpl(Statement pstmt, ResultSet rs, boolean multiple) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.pstmt = pstmt;
		this.rs = rs;
		this.multiple = multiple;
	}
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	protected ResultSetImpl(PreparedStatement pstmt, ResultSet rs, boolean multiple) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.pstmt = pstmt;
		this.rs = rs;
		this.multiple = multiple;
	}
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	protected ResultSetImpl(CallableStatement pstmt, ResultSet rs, boolean multiple) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.pstmt = pstmt;
		this.rs = rs;
		this.multiple = multiple;
	}
	public boolean next() throws SQLException {
		return this.rs.next();
	}
/**
 * 객체를 닫는다.
 * @see kr.xdbc.trace.ConnectionManager.clear()
 */
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
@Deprecated
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
@Deprecated
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return this.rs.getUnicodeStream(columnIndex);
	}
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return this.rs.getBinaryStream(columnIndex);
	}
	public String getString(String columnLabel) throws SQLException {
		return this.rs.getString(columnLabel);
	}
	public boolean getBoolean(String columnLabel) throws SQLException {
		return this.rs.getBoolean(columnLabel);
	}
	public byte getByte(String columnLabel) throws SQLException {
		return this.rs.getByte(columnLabel);
	}
	public short getShort(String columnLabel) throws SQLException {
		return this.rs.getShort(columnLabel);
	}
	public int getInt(String columnLabel) throws SQLException {
		return this.rs.getInt(columnLabel);
	}
	public long getLong(String columnLabel) throws SQLException {
		return this.rs.getLong(columnLabel);
	}
	public float getFloat(String columnLabel) throws SQLException {
		return this.rs.getFloat(columnLabel);
	}
	public double getDouble(String columnLabel) throws SQLException {
		return this.rs.getDouble(columnLabel);
	}
@Deprecated
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		return this.rs.getBigDecimal(columnLabel, scale);
	}
	public byte[] getBytes(String columnLabel) throws SQLException {
		return this.rs.getBytes(columnLabel);
	}
	public Date getDate(String columnLabel) throws SQLException {
		return this.rs.getDate(columnLabel);
	}
	public Time getTime(String columnLabel) throws SQLException {
		return this.rs.getTime(columnLabel);
	}
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return this.rs.getTimestamp(columnLabel);
	}
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		return this.rs.getAsciiStream(columnLabel);
	}
@Deprecated
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		return this.rs.getUnicodeStream(columnLabel);
	}
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		return this.rs.getBinaryStream(columnLabel);
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
	public Object getObject(String columnLabel) throws SQLException {
		return this.rs.getObject(columnLabel);
	}
	public int findColumn(String columnLabel) throws SQLException {
		return this.rs.findColumn(columnLabel);
	}
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return this.rs.getCharacterStream(columnIndex);
	}
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		return this.rs.getCharacterStream(columnLabel);
	}
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return this.rs.getBigDecimal(columnIndex);
	}
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		return this.rs.getBigDecimal(columnLabel);
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
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public boolean rowUpdated() throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			return this.rs.rowUpdated();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public boolean rowInserted() throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			return this.rs.rowInserted();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public boolean rowDeleted() throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			return this.rs.rowDeleted();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNull(int columnIndex) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNull(columnIndex);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBoolean(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateByte(int columnIndex, byte x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateByte(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateShort(int columnIndex, short x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateShort(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateInt(int columnIndex, int x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateInt(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateLong(int columnIndex, long x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateLong(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateFloat(int columnIndex, float x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateFloat(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateDouble(int columnIndex, double x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateDouble(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBigDecimal(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateString(int columnIndex, String x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateString(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBytes(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateDate(int columnIndex, Date x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateDate(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateTime(int columnIndex, Time x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateTime(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateTimestamp(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateAsciiStream(columnIndex, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBinaryStream(columnIndex, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateCharacterStream(columnIndex, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateObject(columnIndex, x, scaleOrLength);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateObject(int columnIndex, Object x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateObject(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNull(String columnLabel) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNull(columnLabel);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBoolean(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateByte(String columnLabel, byte x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateByte(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateShort(String columnLabel, short x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateShort(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateInt(String columnLabel, int x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateInt(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateLong(String columnLabel, long x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateLong(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateFloat(String columnLabel, float x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateFloat(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateDouble(String columnLabel, double x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateDouble(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBigDecimal(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateString(String columnLabel, String x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateString(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBytes(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateDate(String columnLabel, Date x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateDate(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateTime(String columnLabel, Time x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateTime(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateTimestamp(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateAsciiStream(columnLabel, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBinaryStream(columnLabel, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateCharacterStream(columnLabel, reader, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateObject(columnLabel, x, scaleOrLength);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateObject(String columnLabel, Object x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateObject(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void insertRow() throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.insertRow();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateRow() throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateRow();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void deleteRow() throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.deleteRow();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void refreshRow() throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.refreshRow();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void cancelRowUpdates() throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.cancelRowUpdates();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void moveToInsertRow() throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.moveToInsertRow();
		}
	}
	public void moveToCurrentRow() throws SQLException {
		this.rs.moveToCurrentRow();
	}
/**
 * Statement 객체를 반환한다.
 */
	public Statement getStatement() throws SQLException {
		return this.pstmt;
	}
	public Object getObject(int columnIndex, Map<String,Class<?>> map) throws SQLException {
		return this.rs.getObject(columnIndex, map);
	}
	public Ref getRef(int columnIndex) throws SQLException {
		return this.rs.getRef(columnIndex);
	}
	public Blob getBlob(int columnIndex) throws SQLException {
		return this.rs.getBlob(columnIndex);
	}
	public Clob getClob(int columnIndex) throws SQLException {
		return this.rs.getClob(columnIndex);
	}
	public Array getArray(int columnIndex) throws SQLException {
		return this.rs.getArray(columnIndex);
	}
	public Object getObject(String columnLabel, Map<String,Class<?>> map) throws SQLException {
		return this.rs.getObject(columnLabel, map);
	}
	public Ref getRef(String columnLabel) throws SQLException {
		return this.rs.getRef(columnLabel);
	}
	public Blob getBlob(String columnLabel) throws SQLException {
		return this.rs.getBlob(columnLabel);
	}
	public Clob getClob(String columnLabel) throws SQLException {
		return this.rs.getClob(columnLabel);
	}
	public Array getArray(String columnLabel) throws SQLException {
		return this.rs.getArray(columnLabel);
	}
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return this.rs.getDate(columnIndex, cal);
	}
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		return this.rs.getDate(columnLabel, cal);
	}
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return this.rs.getTime(columnIndex, cal);
	}
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return this.rs.getTime(columnLabel, cal);
	}
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		return this.rs.getTimestamp(columnIndex, cal);
	}
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		return this.rs.getTimestamp(columnLabel, cal);
	}
	public URL getURL(int columnIndex) throws SQLException {
		return this.rs.getURL(columnIndex);
	}
	public URL getURL(String columnLabel) throws SQLException {
		return this.rs.getURL(columnLabel);
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateRef(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateRef(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBlob(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBlob(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateClob(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateClob(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateArray(int columnIndex, Array x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateArray(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateArray(String columnLabel, Array x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateArray(columnLabel, x);
		}
	}
	public RowId getRowId(int columnIndex) throws SQLException {
		return this.rs.getRowId(columnIndex);
	}
	public RowId getRowId(String columnLabel) throws SQLException {
		return this.rs.getRowId(columnLabel);
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateRowId(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateRowId(columnLabel, x);
		}
	}
	public int getHoldability() throws SQLException {
		return this.rs.getHoldability();
	}
	public boolean isClosed() throws SQLException {
		return this.rs.isClosed();
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNString(int columnIndex, String nString) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNString(columnIndex, nString);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNString(String columnLabel, String nString) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNString(columnLabel, nString);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNClob(columnIndex, nClob);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNClob(columnLabel, nClob);
		}
	}
	public NClob getNClob(int columnIndex) throws SQLException {
		return this.rs.getNClob(columnIndex);
	}
	public NClob getNClob(String columnLabel) throws SQLException {
		return this.rs.getNClob(columnLabel);
	}
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return this.rs.getSQLXML(columnIndex);
	}
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return this.rs.getSQLXML(columnLabel);
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateSQLXML(columnIndex, xmlObject);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateSQLXML(columnLabel, xmlObject);
		}
	}
	public String getNString(int columnIndex) throws SQLException {
		return this.rs.getNString(columnIndex);
	}
	public String getNString(String columnLabel) throws SQLException {
		return this.rs.getNString(columnLabel);
	}
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return this.rs.getNCharacterStream(columnIndex);
	}
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return this.rs.getNCharacterStream(columnLabel);
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNCharacterStream(columnIndex, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNCharacterStream(columnLabel, reader, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateAsciiStream(columnIndex, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBinaryStream(columnIndex, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateCharacterStream(columnIndex, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateAsciiStream(columnLabel, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBinaryStream(columnLabel, x, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateCharacterStream(columnLabel, reader, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBlob(columnIndex, inputStream, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBlob(columnLabel, inputStream, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateClob(columnIndex, reader, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateClob(columnLabel, reader, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNClob(columnIndex, reader, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNClob(columnLabel, reader, length);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNCharacterStream(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNCharacterStream(columnLabel, reader);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateAsciiStream(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBinaryStream(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateCharacterStream(columnIndex, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateAsciiStream(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBinaryStream(columnLabel, x);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateCharacterStream(columnLabel, reader);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBlob(columnIndex, inputStream);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateBlob(columnLabel, inputStream);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateClob(columnIndex, reader);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateClob(columnLabel, reader);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNClob(columnIndex, reader);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		if(multiple) {
			throw new UnsupportedOperationException();
		} else {
			this.rs.updateNClob(columnLabel, reader);
		}
	}
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		return this.rs.getObject(columnIndex, type);
	}
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		return this.rs.getObject(columnLabel, type);
	}
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if(iface.isAssignableFrom(this.getClass())) {
			return iface.cast(this.getClass());
		} else {
			return this.rs.unwrap(iface);
		}
	}
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		if(iface.isAssignableFrom(this.getClass())) {
			return true;
		} else {
			return this.rs.isWrapperFor(iface);
		}
	}
}