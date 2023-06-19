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
 * API reference 로부터 자동으로 생성한 코드가 아닌 경우 javadoc 을 남긴다.
 * @author HeonJik, KIM
 * @version 0.3
 * @since 0.3
 */
public class StatementImpl implements Statement {
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
	private Statement stmt;
/**
 * 내부적으로 관리하는 객체 (Connection 이 여러개인 경우)
 */
	private Statement[] stmts;
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	protected StatementImpl(Connection con, Statement stmt) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.stmt = stmt;
		this.stmts = null;
	}
/**
 * 객체를 생성한다.
 * @see kr.xdbc.trace.ConnectionManager.mark()
 */
	protected StatementImpl(Connection con, Statement[] stmts) throws SQLException {
		this.index = ConnectionManager.getInstance().mark();
		this.con = con;
		this.stmts = stmts;
	}
/**
 * Statement 가 여러 개인 경우 첫 번째 것을 실행하고 반환받은 ResultSet 을 ResultSetImpl 로 감싸서 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public ResultSet executeQuery(String sql) throws SQLException {
		try {
			if(this.stmts != null) {
				ResultSet rs = new ResultSetImpl(this, this.stmts[0].executeQuery(sql), true);
				if(logger.isLoggable(Level.FINE)) { logger.fine(sql); }
				return rs;
			} else {
				ResultSet rs = new ResultSetImpl(this, this.stmt.executeQuery(sql), false);
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
			if(this.stmts != null) {
				int result = 0;
				for(int i = 0; i < this.stmts.length; i++) {
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
/**
 * 객체를 닫는다.
 * @see kr.xdbc.trace.ConnectionManager.clear()
 */
	public void close() throws SQLException {
		ConnectionManager.getInstance().clear(this.index);
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].close();
			}
		} else {
			this.stmt.close();
		}
	}
	public int getMaxFieldSize() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getMaxFieldSize();
		} else {
			return this.stmt.getMaxFieldSize();
		}
	}
	public void setMaxFieldSize(int max) throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].setMaxFieldSize(max);
			}
		} else {
			this.stmt.setMaxFieldSize(max);
		}
	}
	public int getMaxRows() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getMaxRows();
		} else {
			return this.stmt.getMaxRows();
		}
	}
	public void setMaxRows(int max) throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].setMaxRows(max);
			}
		} else {
			this.stmt.setMaxRows(max);
		}
	}
	public void setEscapeProcessing(boolean enable) throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].setEscapeProcessing(enable);
			}
		} else {
			this.stmt.setEscapeProcessing(enable);
		}
	}
	public int getQueryTimeout() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getQueryTimeout();
		} else {
			return this.stmt.getQueryTimeout();
		}
	}
	public void setQueryTimeout(int seconds) throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].setQueryTimeout(seconds);
			}
		} else {
			this.stmt.setQueryTimeout(seconds);
		}
	}
	public void cancel() throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].cancel();
			}
		} else {
			this.stmt.cancel();
		}
	}
	public SQLWarning getWarnings() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getWarnings();
		} else {
			return this.stmt.getWarnings();
		}
	}
	public void clearWarnings() throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].clearWarnings();
			}
		} else {
			this.stmt.clearWarnings();
		}
	}
	public void setCursorName(String name) throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].setCursorName(name);
			}
		} else {
			this.stmt.setCursorName(name);
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
			if(this.stmts != null) {
				for(int i = 0; i < this.stmts.length; i++) {
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
/**
 * Statement 가 여러 개인 경우 첫 번째 것의 ResultSet 을 ResultSetImpl 로 감싸서 반환한다.
 */
	public ResultSet getResultSet() throws SQLException {
		if(this.stmts != null) {
			return new ResultSetImpl(this, this.stmts[0].getResultSet(), true);
		} else {
			return new ResultSetImpl(this, this.stmt.getResultSet(), false);
		}
	}
	public int getUpdateCount() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getUpdateCount();
		} else {
			return this.stmt.getUpdateCount();
		}
	}
	public boolean getMoreResults() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getMoreResults();
		} else {
			return this.stmt.getMoreResults();
		}
	}
	public void setFetchDirection(int direction) throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].setFetchDirection(direction);
			}
		} else {
			this.stmt.setFetchDirection(direction);
		}
	}
	public int getFetchDirection() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getFetchDirection();
		} else {
			return this.stmt.getFetchDirection();
		}
	}
	public void setFetchSize(int rows) throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].setFetchSize(rows);
			}
		} else {
			this.stmt.setFetchSize(rows);
		}
	}
	public int getFetchSize() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getFetchSize();
		} else {
			return this.stmt.getFetchSize();
		}
	}
	public int getResultSetConcurrency() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getResultSetConcurrency();
		} else {
			return this.stmt.getResultSetConcurrency();
		}
	}
	public int getResultSetType() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getResultSetType();
		} else {
			return this.stmt.getResultSetType();
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public void addBatch(String sql) throws SQLException {
		try {
			if(this.stmts != null) {
				for(int i = 0; i < this.stmts.length; i++) {
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
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].clearBatch();
			}
		} else {
			this.stmt.clearBatch();
		}
	}
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 */
	public int[] executeBatch() throws SQLException {
		if(this.stmts != null) {
			int[] r = new int[0];
			for(int i = 0; i < this.stmts.length; i++) {
				r = this.stmts[i].executeBatch();
			}
			return r;
		} else {
			return this.stmt.executeBatch();
		}
	}
/**
 * ConnectionImpl 을 반환한다.
 */
	public Connection getConnection() throws SQLException {
		return this.con;
	}
	public boolean getMoreResults(int current) throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getMoreResults(current);
		} else {
			return this.stmt.getMoreResults(current);
		}
	}
	public ResultSet getGeneratedKeys() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].getGeneratedKeys();
		} else {
			return this.stmt.getGeneratedKeys();
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
			if(this.stmts != null) {
				for(int i = 0; i < this.stmts.length; i++) {
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
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		try {
			int result = 0;
			if(this.stmts != null) {
				for(int i = 0; i < this.stmts.length; i++) {
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
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		try {
			int result = 0;
			if(this.stmts != null) {
				for(int i = 0; i < this.stmts.length; i++) {
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
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			boolean issucess = false;
			if(this.stmts != null) {
				for(int i = 0; i < this.stmts.length; i++) {
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
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		try {
			boolean issucess = false;
			if(this.stmts != null) {
				for(int i = 0; i < this.stmts.length; i++) {
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
/**
 * Statement 가 여러 개인 경우 순차적으로 실행하고, 마지막 실행결과를 반환한다.
 * 실행한 sql 을 Level.FINE 로그로 남긴다.
 * SQLException이 발생한 경우 실패한 sql 을 Level.SEVERE 로그로 남긴다.
 */
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		try {
			boolean issucess = false;
			if(this.stmts != null) {
				for(int i = 0; i < this.stmts.length; i++) {
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
		if(this.stmts != null) {
			return this.stmts[0].getResultSetHoldability();
		} else {
			return this.stmt.getResultSetHoldability();
		}
	}
/**
 * 연결이 여러 개인 경우 모든 isClosed() 가 false 인 경우에만, false 를 반환한다.
 */
	public boolean isClosed() throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				if(this.stmts[0].isClosed()) {
					return true;
				}
			}
			return false;
		} else {
			return this.stmt.isClosed();
		}
	}
	public void setPoolable(boolean poolable) throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].setPoolable(poolable);
			}
		} else {
			this.stmt.setPoolable(poolable);
		}
	}
	public boolean isPoolable() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].isPoolable();
		} else {
			return this.stmt.isPoolable();
		}
	}
	public void closeOnCompletion() throws SQLException {
		if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				this.stmts[i].closeOnCompletion();
			}
		} else {
			this.stmt.closeOnCompletion();
		}
	}
	public boolean isCloseOnCompletion() throws SQLException {
		if(this.stmts != null) {
			return this.stmts[0].isCloseOnCompletion();
		} else {
			return this.stmt.isCloseOnCompletion();
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
		} else if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				if(this.stmts[i].isWrapperFor(iface)) {
					return this.stmts[i].unwrap(iface);
				}
				if(i == this.stmts.length - 1) {
					return this.stmts[i].unwrap(iface);
				}
			}
			throw new SQLException("Cannot unwrap to " + iface.getName());
		} else {
			return this.stmt.unwrap(iface);
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
		} else if(this.stmts != null) {
			for(int i = 0; i < this.stmts.length; i++) {
				if(this.stmts[i].isWrapperFor(iface)) {
					return true;
				}
			}
			return false;
		} else {
			return this.stmt.isWrapperFor(iface);
		}
	}
}