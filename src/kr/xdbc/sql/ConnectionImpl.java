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
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.SQLWarning;
import java.util.Map;
import java.sql.Savepoint;
import kr.xdbc.trace.ConnectionManager;

/**
 * Connection Wrapper

 * @author HeonJik, KIM
 * @version 0.2
 * @since 0.1
 */

public final class ConnectionImpl implements Connection {
	private int index;
	private Connection con;
	private Connection[] cons;
	public ConnectionImpl(Connection con) {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.cons = null;
	}
	public ConnectionImpl(Connection[] cons) {
		this.index = ConnectionManager.getInstance().mark();
		this.cons = cons;
	}
	public Statement createStatement() throws SQLException {
		if(cons != null) {
			Statement[] stmts = new Statement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].createStatement();
			}
			return new StatementImpl(this, stmts);
		} else {
			return new StatementImpl(this, con.createStatement());
		}
	}
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		if(cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].prepareStatement(sql);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql), sql);
		}
	}
	public CallableStatement prepareCall(String sql) throws SQLException {
		if(cons != null) {
			CallableStatement[] stmts = new CallableStatement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].prepareCall(sql);
			}
			return new CallableStatementImpl(this, stmts, sql);
		} else {
			return new CallableStatementImpl(this, con.prepareCall(sql), sql);
		}
	}
	public String nativeSQL(String sql) throws SQLException {
		if(cons != null) {
			return this.cons[0].nativeSQL(sql);
		} else {
			return this.con.nativeSQL(sql);
		}
	}
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setAutoCommit(autoCommit);
			}
		} else {
			this.con.setAutoCommit(autoCommit);
		}
	}
	public boolean getAutoCommit() throws SQLException {
		if(cons != null) {
			return this.cons[0].getAutoCommit();
		} else {
			return this.con.getAutoCommit();
		}
	}
	public void commit() throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].commit();
			}
		} else {
			this.con.commit();
		}
	}
	public void rollback() throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].rollback();
			}
		} else {
			this.con.rollback();
		}
	}
	public void close() throws SQLException {
		ConnectionManager.getInstance().clear(this.index);
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].close();
			}
		} else {
			this.con.close();
		}
	}
	public boolean isClosed() throws SQLException {
		if(cons != null) {
			return this.cons[0].isClosed();
		} else {
			return this.con.isClosed();
		}
	}
	public DatabaseMetaData getMetaData() throws SQLException {
		if(cons != null) {
			return this.cons[0].getMetaData();
		} else {
			return this.con.getMetaData();
		}
	}
	public void setReadOnly(boolean readOnly) throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setReadOnly(readOnly);
			}
		} else {
			this.con.setReadOnly(readOnly);
		}
	}
	public boolean isReadOnly() throws SQLException {
		if(cons != null) {
			return this.cons[0].isReadOnly();
		} else {
			return this.con.isReadOnly();
		}
	}
	public void setCatalog(String catalog) throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setCatalog(catalog);
			}
		} else {
			this.con.setCatalog(catalog);
		}
	}
	public String getCatalog() throws SQLException {
		if(cons != null) {
			return this.cons[0].getCatalog();
		} else {
			return this.con.getCatalog();
		}
	}
	public void setTransactionIsolation(int level) throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setTransactionIsolation(level);
			}
		} else {
			this.con.setTransactionIsolation(level);
		}
	}
	public int getTransactionIsolation() throws SQLException {
		if(cons != null) {
			return this.cons[0].getTransactionIsolation();
		} else {
			return this.con.getTransactionIsolation();
		}
	}
	public SQLWarning getWarnings() throws SQLException {
		if(cons != null) {
			return this.cons[0].getWarnings();
		} else {
			return this.con.getWarnings();
		}
	}
	public void clearWarnings() throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].clearWarnings();
			}
		} else {
			this.con.clearWarnings();
		}
	}
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		if(cons != null) {
			Statement[] stmts = new Statement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].createStatement(resultSetType, resultSetConcurrency);
			}
			return new StatementImpl(this, stmts);
		} else {
			return new StatementImpl(this, this.con.createStatement(resultSetType, resultSetConcurrency));
		}
	}
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		if(cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].prepareStatement(sql, resultSetType, resultSetConcurrency);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, resultSetType, resultSetConcurrency), sql);
		}
	}
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		if(cons != null) {
			CallableStatement[] stmts = new CallableStatement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].prepareCall(sql, resultSetType, resultSetConcurrency);
			}
			return new CallableStatementImpl(this, stmts, sql);
		} else {
			return new CallableStatementImpl(this, this.con.prepareCall(sql, resultSetType, resultSetConcurrency), sql);
		}
	}
	public Map getTypeMap() throws SQLException {
		if(cons != null) {
			return this.cons[0].getTypeMap();
		} else {
			return this.con.getTypeMap();
		}
	}
	public void setTypeMap(Map arg0) throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setTypeMap(arg0);
			}
		} else {
			this.con.setTypeMap(arg0);
		}
	}
	public void setHoldability(int holdability) throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setHoldability(holdability);
			}
		} else {
			this.con.setHoldability(holdability);
		}
	}
	public int getHoldability() throws SQLException {
		if(cons != null) {
			return this.cons[0].getHoldability();
		} else {
			return this.con.getHoldability();
		}
	}
	public Savepoint setSavepoint() throws SQLException {
		if(cons != null) {
			Savepoint p = null;
			for(int i = 0; i < cons.length; i++) {
				p = cons[i].setSavepoint();
			}
			return p;
		} else {
			return this.con.setSavepoint();
		}
	}
	public Savepoint setSavepoint(String name) throws SQLException {
		if(cons != null) {
			Savepoint p = null;
			for(int i = 0; i < cons.length; i++) {
				p = cons[i].setSavepoint(name);
			}
			return p;
		} else {
			return this.con.setSavepoint(name);
		}
	}
	public void rollback(Savepoint savepoint) throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].rollback(savepoint);
			}
		} else {
			this.con.rollback(savepoint);
		}
	}
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].releaseSavepoint(savepoint);
			}
		} else {
			this.con.releaseSavepoint(savepoint);
		}
	}
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		if(cons != null) {
			Statement[] stmts = new Statement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
			}
			return new StatementImpl(this, stmts);
		} else {
			return new StatementImpl(this, this.con.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
		}
	}
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		if(cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability), sql);
		}
	}
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		if(cons != null) {
			CallableStatement[] stmts = new CallableStatement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
			}
			return new CallableStatementImpl(this, stmts, sql);
		} else {
			return new CallableStatementImpl(this, this.con.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability), sql);
		}
	}
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		if(cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].prepareStatement(sql, autoGeneratedKeys);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, autoGeneratedKeys), sql);
		}
	}
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		if(cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].prepareStatement(sql, columnIndexes);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, columnIndexes), sql);
		}
	}
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		if(cons != null) {
			PreparedStatement[] stmts = new PreparedStatement[this.cons.length];
			for(int i = 0; i < cons.length; i++) {
				stmts[i] = cons[i].prepareStatement(sql, columnNames);
			}
			return new PreparedStatementImpl(this, stmts, sql);
		} else {
			return new PreparedStatementImpl(this, this.con.prepareStatement(sql, columnNames), sql);
		}
	}
	public java.lang.Object unwrap(java.lang.Class arg0) throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].unwrap(arg0);
		} else {
			return this.con.unwrap(arg0);
		}
	}
	public boolean isWrapperFor(java.lang.Class arg0) throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].isWrapperFor(arg0);
		} else {
			return this.con.isWrapperFor(arg0);
		}
	}
	
	
	public void abort(java.util.concurrent.Executor arg0) throws java.sql.SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].abort(arg0);
			}
		} else {
			this.con.abort(arg0);
		}
	}
	public java.sql.Array createArrayOf(java.lang.String arg0, java.lang.Object[] arg1) throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].createArrayOf(arg0, arg1);
		} else {
			return this.con.createArrayOf(arg0, arg1);
		}
	}
	public java.sql.Blob createBlob() throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].createBlob();
		} else {
			return this.con.createBlob();
		}
	}
	public java.sql.Clob createClob() throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].createClob();
		} else {
			return this.con.createClob();
		}
	}
	public java.sql.NClob createNClob() throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].createNClob();
		} else {
			return this.con.createNClob();
		}
	}
	public java.sql.SQLXML createSQLXML() throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].createSQLXML();
		} else {
			return this.con.createSQLXML();
		}
	}
	public java.sql.Struct createStruct(java.lang.String arg0, java.lang.Object[] arg1) throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].createStruct(arg0, arg1);
		} else {
			return this.con.createStruct(arg0, arg1);
		}
	}
	public java.util.Properties getClientInfo() throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].getClientInfo();
		} else {
			return this.con.getClientInfo();
		}
	}
	public java.lang.String getClientInfo(java.lang.String arg0) throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].getClientInfo(arg0);
		} else {
			return this.con.getClientInfo(arg0);
		}
	}
	public int getNetworkTimeout() throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].getNetworkTimeout();
		} else {
			return this.con.getNetworkTimeout();
		}
	}
	public java.lang.String getSchema() throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].getSchema();
		} else {
			return this.con.getSchema();
		}
	}
	public boolean isValid(int arg0) throws java.sql.SQLException {
		if(cons != null) {
			return this.cons[0].isValid(arg0);
		} else {
			return this.con.isValid(arg0);
		}
	}
	public void setClientInfo(java.util.Properties arg0) throws java.sql.SQLClientInfoException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setClientInfo(arg0);
			}
		} else {
			this.con.setClientInfo(arg0);
		}
	}
	public void setClientInfo(java.lang.String arg0, java.lang.String arg1) throws java.sql.SQLClientInfoException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setClientInfo(arg0, arg1);
			}
		} else {
			this.con.setClientInfo(arg0, arg1);
		}
	}
	public void setNetworkTimeout(java.util.concurrent.Executor arg0, int arg1) throws java.sql.SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setNetworkTimeout(arg0, arg1);
			}
		} else {
			this.con.setNetworkTimeout(arg0, arg1);
		}
	}
	public void setSchema(java.lang.String arg0) throws java.sql.SQLException {
		if(cons != null) {
			for(int i = 0; i < cons.length; i++) {
				cons[i].setSchema(arg0);
			}
		} else {
			this.con.setSchema(arg0);
		}
	}
}

