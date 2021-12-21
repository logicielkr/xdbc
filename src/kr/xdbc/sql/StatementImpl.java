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

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import kr.xdbc.trace.ConnectionManager;
/**
 * Statement Wrapper

 * @author HeonJik, KIM
 * @version 0.2
 * @since 0.1
 */
public class StatementImpl implements Statement {
	private int index;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Connection con;
	private Statement stmt;
	private Statement[] stmts;
	public StatementImpl(Connection con, Statement stmt) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.stmt = stmt;
		this.stmts = null;
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
	public StatementImpl(Connection con, Statement[] stmts) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.stmts = stmts;
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
	public ResultSet executeQuery(String sql) throws SQLException {
		try {
			if(stmts != null) {
				ResultSet rs = new ResultSetImpl(this, this.stmts[0].executeQuery(sql));
				if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
				return rs;
			} else {
				ResultSet rs = new ResultSetImpl(this, this.stmt.executeQuery(sql));
				if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
				return rs;
			}
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public int executeUpdate(String sql) throws SQLException {
		try {
			if(stmts != null) {
				int result = 0;
				for(int i = 0; i < stmts.length; i++) {
					result = this.stmts[i].executeUpdate(sql);
				}
				if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
				return result;
			} else {
				int result = this.stmt.executeUpdate(sql);
				if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
				return result;
			}
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public void close() throws SQLException {
		ConnectionManager.getInstance().clear(this.index);
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].close();
			}
		} else {
			this.stmt.close();
		}
	}
	public int getMaxFieldSize() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getMaxFieldSize();
		} else {
			return this.stmt.getMaxFieldSize();
		}
	}
	public void setMaxFieldSize(int max) throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].setMaxFieldSize(max);
			}
		} else {
		this.stmt.setMaxFieldSize(max);
		}
	}
	public int getMaxRows() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getMaxRows();
		} else {
			return this.stmt.getMaxRows();
		}
	}
	public void setMaxRows(int max) throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].setMaxRows(max);
			}
		} else {
		this.stmt.setMaxRows(max);
		}
	}
	public void setEscapeProcessing(boolean enable) throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].setEscapeProcessing(enable);
			}
		} else {
		this.stmt.setEscapeProcessing(enable);
		}
	}
	public int getQueryTimeout() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getQueryTimeout();
		} else {
			return this.stmt.getQueryTimeout();
		}
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].setQueryTimeout(seconds);
			}
		} else {
		this.stmt.setQueryTimeout(seconds);
		}
	}
	public void cancel() throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].cancel();
			}
		} else {
		this.stmt.cancel();
		}
	}
	
	public SQLWarning getWarnings() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getWarnings();
		} else {
		return this.stmt.getWarnings();
		}
	}
	public void clearWarnings() throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].clearWarnings();
			}
		} else {
		this.stmt.clearWarnings();
		}
	}
	public void setCursorName(String name) throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].setCursorName(name);
			}
		} else {
		this.stmt.setCursorName(name);
		}
	}
	public boolean execute(String sql) throws SQLException {
		try {
			boolean issucess = false;
			if(stmts != null) {
				for(int i = 0; i < stmts.length; i++) {
					issucess = this.stmts[i].execute(sql);
				}
			} else {
				issucess = this.stmt.execute(sql);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public ResultSet getResultSet() throws SQLException {
		if(stmts != null) {
		return new ResultSetImpl(this, this.stmts[0].getResultSet());
		} else {
			return new ResultSetImpl(this, this.stmt.getResultSet());
		}
	}
	public int getUpdateCount() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getUpdateCount();
		} else {
		return this.stmt.getUpdateCount();
		}
	}
	public boolean getMoreResults() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getMoreResults();
		} else {
		return this.stmt.getMoreResults();
		}
	}
	public void setFetchDirection(int direction) throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].setFetchDirection(direction);
			}
		} else {
		this.stmt.setFetchDirection(direction);
		}
	}
	public int getFetchDirection() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getFetchDirection();
		} else {
		return this.stmt.getFetchDirection();
		}
	}
	public void setFetchSize(int rows) throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].setFetchSize(rows);
			}
		} else {
		this.stmt.setFetchSize(rows);
		}
	}
	public int getFetchSize() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getFetchSize();
		} else {
		return this.stmt.getFetchSize();
		}
	}
	public int getResultSetConcurrency() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getResultSetConcurrency();
		} else {
		return this.stmt.getResultSetConcurrency();
		}
	}
	public int getResultSetType() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getResultSetType();
		} else {
		return this.stmt.getResultSetType();
		}
	}
	public void addBatch(String sql) throws SQLException {
		try {
			if(stmts != null) {
				for(int i = 0; i < stmts.length; i++) {
					this.stmts[i].addBatch(sql);
				}
			} else {
				this.stmt.addBatch(sql);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public void clearBatch() throws SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].clearBatch();
			}
		} else {
		this.stmt.clearBatch();
		}
	}
	public int[] executeBatch() throws SQLException {
		if(stmts != null) {
			int[] r = new int[0];
			for(int i = 0; i < stmts.length; i++) {
				r = this.stmts[i].executeBatch();
			}
			return r;
		} else {
		return this.stmt.executeBatch();
		}
	}
	public Connection getConnection() throws SQLException {
		return this.con;
	}
	public boolean getMoreResults(int current) throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getMoreResults(current);
		} else {
		return this.stmt.getMoreResults(current);
		}
	}
	public ResultSet getGeneratedKeys() throws SQLException {
		if(stmts != null) {
		return new ResultSetImpl(this, this.stmts[0].getGeneratedKeys());
		} else {
			return new ResultSetImpl(this, this.stmt.getGeneratedKeys());
		}
	}
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			int result = 0;
			if(stmts != null) {
				for(int i = 0; i < stmts.length; i++) {
					result = this.stmts[i].executeUpdate(sql, autoGeneratedKeys);
				}
			} else {
				result = this.stmt.executeUpdate(sql, autoGeneratedKeys);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		try {
			int result = 0;
			if(stmts != null) {
				for(int i = 0; i < stmts.length; i++) {
					result = this.stmts[i].executeUpdate(sql, columnIndexes);
				}
			} else {
				result = this.stmt.executeUpdate(sql, columnIndexes);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		try {
			int result = 0;
			if(stmts != null) {
				for(int i = 0; i < stmts.length; i++) {
					result = this.stmts[i].executeUpdate(sql, columnNames);
				}
			} else {
			result = this.stmt.executeUpdate(sql, columnNames);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return result;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			boolean issucess = false;
			if(stmts != null) {
				for(int i = 0; i < stmts.length; i++) {
					issucess = this.stmts[i].execute(sql, autoGeneratedKeys);
				}
			} else {
				issucess = this.stmt.execute(sql, autoGeneratedKeys);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		try {
			boolean issucess = false;
			if(stmts != null) {
				for(int i = 0; i < stmts.length; i++) {
					issucess = this.stmts[i].execute(sql, columnIndexes);
				}
			} else {
				issucess = this.stmt.execute(sql, columnIndexes);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public boolean execute(String sql, String[] columnNames)throws SQLException {
		try {
			boolean issucess = false;
			if(stmts != null) {
				for(int i = 0; i < stmts.length; i++) {
					issucess = this.stmts[i].execute(sql, columnNames);
				}
			} else {
				issucess = this.stmt.execute(sql, columnNames);
			}
			if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
			return issucess;
		} catch (SQLException e) {
			if(logger.isLoggable(Level.SEVERE)) { logger.severe(sql); }
			throw e;
		}
	}
	public int getResultSetHoldability() throws SQLException {
		if(stmts != null) {
			return this.stmts[0].getResultSetHoldability();
		} else {
		return this.stmt.getResultSetHoldability();
		}
	}
	
	
	
	public void closeOnCompletion() throws java.sql.SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].closeOnCompletion();
			}
		} else {
			this.stmt.closeOnCompletion();
		}
	}
	public boolean isClosed() throws java.sql.SQLException {
		if(stmts != null) {
			return this.stmts[0].isClosed();
		} else {
		return this.stmt.isClosed();
		}
	}
	public boolean isCloseOnCompletion() throws java.sql.SQLException {
		if(stmts != null) {
			return this.stmts[0].isCloseOnCompletion();
		} else {
		return this.stmt.isCloseOnCompletion();
		}
	}
	public boolean isPoolable() throws java.sql.SQLException {
		if(stmts != null) {
			return this.stmts[0].isPoolable();
		} else {
		return this.stmt.isPoolable();
		}
	}
	public void setPoolable(boolean arg0) throws java.sql.SQLException {
		if(stmts != null) {
			for(int i = 0; i < stmts.length; i++) {
				this.stmts[i].setPoolable(arg0);
			}
		} else {
		this.stmt.setPoolable(arg0);
		}
	}
	
	public java.lang.Object unwrap(java.lang.Class arg0) throws java.sql.SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				if(i == this.stmts.length - 1) {
					return stmts[i].unwrap(arg0);
				} else {
					stmts[i].unwrap(arg0);
				}
			}
			return null;
		} else {
		return this.stmt.unwrap(arg0);
		}
	}
	public boolean isWrapperFor(java.lang.Class arg0) throws java.sql.SQLException {
		if(stmts != null) {
			return this.stmts[0].isWrapperFor(arg0);
		} else {
		return this.stmt.isWrapperFor(arg0);
		}
	}
}
