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

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.SQLWarning;
import java.util.Map;
import java.sql.Savepoint;
import kr.xdbc.trace.ConnectionManager;
import java.util.concurrent.Executor;
import java.util.Properties;
import java.sql.SQLClientInfoException;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.Blob;
import java.sql.SQLXML;
import java.sql.Array;
import java.sql.Struct;

/**
 * Connection Wrapper
 * API reference 로부터 자동으로 생성한 코드가 아닌 경우 javadoc 을 남긴다.
 * Connection 이 여러 개인 경우 Savepoint 와 관련된 것들은 지원하지 않는다.
 * @author HeonJik, KIM
 * @version 0.3
 * @since 0.3
 */

public final class ConnectionImpl implements Connection {
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
	private Connection[] cons;
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	public ConnectionImpl(Connection con) {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.cons = null;
	}
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	public ConnectionImpl(Connection[] cons) {
		this.index = ConnectionManager.getInstance().mark();
		this.cons = cons;
	}
	public Statement createStatement() throws SQLException {
		if(this.cons != null) {
			Statement[] stmts = new Statement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].createStatement();
			}
			return new StatementImpl(this, stmts);
		} else {
			return new StatementImpl(this, this.con.createStatement());
		}
	}
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		if(this.cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].prepareStatement(sql);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql), sql);
		}
	}
	public CallableStatement prepareCall(String sql) throws SQLException {
		if(this.cons != null) {
			CallableStatement[] stmts = new CallableStatement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].prepareCall(sql);
			}
			return new CallableStatementImpl(this, stmts, sql);
		} else {
			return new CallableStatementImpl(this, this.con.prepareCall(sql), sql);
		}
	}
	public String nativeSQL(String sql) throws SQLException {
		if(this.cons != null) {
			return this.cons[0].nativeSQL(sql);
		} else {
			return this.con.nativeSQL(sql);
		}
	}
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setAutoCommit(autoCommit);
			}
		} else {
			this.con.setAutoCommit(autoCommit);
		}
	}
	public boolean getAutoCommit() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getAutoCommit();
		} else {
			return this.con.getAutoCommit();
		}
	}
	public void commit() throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].commit();
			}
		} else {
			this.con.commit();
		}
	}
	public void rollback() throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].rollback();
			}
		} else {
			this.con.rollback();
		}
	}
/**
 * 객체를 닫는다.
 * @see kr.xdbc.trace.ConnectionManager.clear()
 */
	public void close() throws SQLException {
		ConnectionManager.getInstance().clear(this.index);
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].close();
			}
		} else {
			this.con.close();
		}
	}
/**
 * 연결이 여러 개인 경우 모든 isClosed() 가 false 인 경우에만, false 를 반환한다.
 */
	public boolean isClosed() throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				if(this.cons[0].isClosed()) {
					return true;
				}
			}
			return false;
		} else {
			return this.con.isClosed();
		}
	}
	public DatabaseMetaData getMetaData() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getMetaData();
		} else {
			return this.con.getMetaData();
		}
	}
	public void setReadOnly(boolean readOnly) throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setReadOnly(readOnly);
			}
		} else {
			this.con.setReadOnly(readOnly);
		}
	}
	public boolean isReadOnly() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].isReadOnly();
		} else {
			return this.con.isReadOnly();
		}
	}
	public void setCatalog(String catalog) throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setCatalog(catalog);
			}
		} else {
			this.con.setCatalog(catalog);
		}
	}
	public String getCatalog() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getCatalog();
		} else {
			return this.con.getCatalog();
		}
	}
	public void setTransactionIsolation(int level) throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setTransactionIsolation(level);
			}
		} else {
			this.con.setTransactionIsolation(level);
		}
	}
	public int getTransactionIsolation() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getTransactionIsolation();
		} else {
			return this.con.getTransactionIsolation();
		}
	}
	public SQLWarning getWarnings() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getWarnings();
		} else {
			return this.con.getWarnings();
		}
	}
	public void clearWarnings() throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].clearWarnings();
			}
		} else {
			this.con.clearWarnings();
		}
	}
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		if(this.cons != null) {
			Statement[] stmts = new Statement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].createStatement(resultSetType, resultSetConcurrency);
			}
			return new StatementImpl(this, stmts);
		} else {
			return new StatementImpl(this, this.con.createStatement(resultSetType, resultSetConcurrency));
		}
	}
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		if(this.cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].prepareStatement(sql, resultSetType, resultSetConcurrency);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, resultSetType, resultSetConcurrency), sql);
		}
	}
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		if(this.cons != null) {
			CallableStatement[] stmts = new CallableStatement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].prepareCall(sql, resultSetType, resultSetConcurrency);
			}
			return new CallableStatementImpl(this, stmts, sql);
		} else {
			return new CallableStatementImpl(this, this.con.prepareCall(sql, resultSetType, resultSetConcurrency), sql);
		}
	}
	public Map<String,Class<?>> getTypeMap() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getTypeMap();
		} else {
			return this.con.getTypeMap();
		}
	}
	public void setTypeMap(Map<String,Class<?>> map) throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setTypeMap(map);
			}
		} else {
			this.con.setTypeMap(map);
		}
	}
	public void setHoldability(int holdability) throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setHoldability(holdability);
			}
		} else {
			this.con.setHoldability(holdability);
		}
	}
	public int getHoldability() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getHoldability();
		} else {
			return this.con.getHoldability();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public Savepoint setSavepoint() throws SQLException {
		if(this.cons != null) {
			throw new UnsupportedOperationException();
		} else {
			return this.con.setSavepoint();
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public Savepoint setSavepoint(String name) throws SQLException {
		if(this.cons != null) {
			throw new UnsupportedOperationException();
		} else {
			return this.con.setSavepoint(name);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void rollback(Savepoint savepoint) throws SQLException {
		if(this.cons != null) {
			throw new UnsupportedOperationException();
		} else {
			this.con.rollback(savepoint);
		}
	}
/**
 * Connection 이 여러 개인 경우 UnsupportedOperationException 을 발생시킨다.
 */
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		if(this.cons != null) {
			throw new UnsupportedOperationException();
		} else {
			this.con.releaseSavepoint(savepoint);
		}
	}
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		if(this.cons != null) {
			Statement[] stmts = new Statement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
			}
			return new StatementImpl(this, stmts);
		} else {
			return new StatementImpl(this, this.con.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
		}
	}
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		if(this.cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability), sql);
		}
	}
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		if(this.cons != null) {
			CallableStatement[] stmts = new CallableStatement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
			}
			return new CallableStatementImpl(this, stmts, sql);
		} else {
			return new CallableStatementImpl(this, this.con.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability), sql);
		}
	}
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		if(this.cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].prepareStatement(sql, autoGeneratedKeys);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, autoGeneratedKeys), sql);
		}
	}
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		if(this.cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].prepareStatement(sql, columnIndexes);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, columnIndexes), sql);
		}
	}
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		if(this.cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < this.cons.length; i++) {
				stmts[i] = this.cons[i].prepareStatement(sql, columnNames);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, columnNames), sql);
		}
	}
	public Clob createClob() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].createClob();
		} else {
			return this.con.createClob();
		}
	}
	public Blob createBlob() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].createBlob();
		} else {
			return this.con.createBlob();
		}
	}
	public NClob createNClob() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].createNClob();
		} else {
			return this.con.createNClob();
		}
	}
	public SQLXML createSQLXML() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].createSQLXML();
		} else {
			return this.con.createSQLXML();
		}
	}
	public boolean isValid(int timeout) throws SQLException {
		if(this.cons != null) {
			return this.cons[0].isValid(timeout);
		} else {
			return this.con.isValid(timeout);
		}
	}
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setClientInfo(name, value);
			}
		} else {
			this.con.setClientInfo(name, value);
		}
	}
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setClientInfo(properties);
			}
		} else {
			this.con.setClientInfo(properties);
		}
	}
	public String getClientInfo(String name) throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getClientInfo(name);
		} else {
			return this.con.getClientInfo(name);
		}
	}
	public Properties getClientInfo() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getClientInfo();
		} else {
			return this.con.getClientInfo();
		}
	}
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		if(this.cons != null) {
			return this.cons[0].createArrayOf(typeName, elements);
		} else {
			return this.con.createArrayOf(typeName, elements);
		}
	}
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		if(this.cons != null) {
			return this.cons[0].createStruct(typeName, attributes);
		} else {
			return this.con.createStruct(typeName, attributes);
		}
	}
	public void setSchema(String schema) throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setSchema(schema);
			}
		} else {
			this.con.setSchema(schema);
		}
	}
	public String getSchema() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getSchema();
		} else {
			return this.con.getSchema();
		}
	}
/**
 * 연결을 종료한다.
 * 
 * @see kr.xdbc.trace.ConnectionManager.clear()
 */
	public void abort(Executor executor) throws SQLException {
		ConnectionManager.getInstance().clear(this.index);
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].abort(executor);
			}
		} else {
			this.con.abort(executor);
		}
	}
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				this.cons[i].setNetworkTimeout(executor, milliseconds);
			}
		} else {
			this.con.setNetworkTimeout(executor, milliseconds);
		}
	}
	public int getNetworkTimeout() throws SQLException {
		if(this.cons != null) {
			return this.cons[0].getNetworkTimeout();
		} else {
			return this.con.getNetworkTimeout();
		}
	}
/**
 * this.getClass() 를 먼저 반환한다.
 * 연결이 여러 개인 경우, 반환할 수 있는 것이 1개라도 있으면 반환한다.
 * 반환할 것이 없으면, 마지막 객체의 unwrap 결과를 반환한다.
 */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if(this.isClosed()) {
			throw new SQLException("This connection has been closed.");
		} else if(iface.isAssignableFrom(this.getClass())) {
			return iface.cast(this.getClass());
		} else if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				if(this.cons[i].isWrapperFor(iface)) {
					return this.cons[i].unwrap(iface);
				}
				if(i == this.cons.length - 1) {
					return this.cons[i].unwrap(iface);
				}
			}
			throw new SQLException("Cannot unwrap to " + iface.getName());
		} else {
			return this.con.unwrap(iface);
		}
	}
/**
 * this.getClass() 이면 true 를 반환한다.
 * 연결이 여러개인 경우, 순차적으로 검사해서 1개라도 true 인 경우, true 를 반환한다.
 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		if(this.isClosed()) {
			throw new SQLException("This connection has been closed.");
		} else if(iface.isAssignableFrom(this.getClass())) {
			return true;
		} else if(this.cons != null) {
			for(int i = 0; i < this.cons.length; i++) {
				if(this.cons[i].isWrapperFor(iface)) {
					return true;
				}
			}
			return false;
		} else {
			return this.con.isWrapperFor(iface);
		}
	}
}