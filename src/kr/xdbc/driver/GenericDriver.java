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

package kr.xdbc.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import kr.xdbc.java8.sql.ConnectionImpl;
import kr.xdbc.util.StringUtil;
import kr.xdbc.util.DBUtil;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.DriverPropertyInfo;
import java.sql.Driver;

/**
 * GenericDriver Wrapper for Java 8
 * Java 7 은 ../java7/driver 에 있다.
 * @author HeonJik, KIM
 * @version 0.3
 * @since 0.1
 */

public class GenericDriver implements java.sql.Driver {
	public static final String PREFIX = "xdbc:jdbc:";
	private Logger logger = Logger.getLogger(this.getClass().getName());
	static {
		/*
		try { DriverManager.registerDriver(new org.apache.derby.jdbc.AutoloadedDriver()); } catch (SQLException e) { }
		try { DriverManager.registerDriver(new org.sqlite.JDBC()); } catch (SQLException e) { }
		try { DriverManager.registerDriver(new org.postgresql.Driver()); } catch (SQLException e) { }
		try { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); } catch (SQLException e) { }
		try { DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver()); } catch (SQLException e) { }
		try { DriverManager.registerDriver(new org.h2.Driver()); } catch (SQLException e) { }
		try { DriverManager.registerDriver(new org.mariadb.jdbc.Driver()); } catch (SQLException e) { }
		*/
		try { Class.forName("org.apache.derby.jdbc.AutoloadedDriver");} catch(ClassNotFoundException e) {}
		try { Class.forName("org.sqlite.JDBC");} catch(ClassNotFoundException e) {}
		try { Class.forName("org.postgresql.Driver");} catch(ClassNotFoundException e) {}
		try { Class.forName("oracle.jdbc.driver.OracleDriver");} catch(ClassNotFoundException e) {}
		try { Class.forName("org.hsqldb.jdbc.JDBCDriver");} catch(ClassNotFoundException e) {}
		try { Class.forName("org.h2.Driver");} catch(ClassNotFoundException e) {}
		try { Class.forName("org.mariadb.jdbc.Driver");} catch(ClassNotFoundException e) {}
		try { Class.forName("com.tmax.tibero.jdbc.TbDriver");} catch(ClassNotFoundException e) {}
		
		try { DriverManager.registerDriver(new kr.xdbc.driver.GenericDriver()); } catch (SQLException e) { }
		
	}
	public java.sql.Connection connect(String url, Properties prop) throws SQLException {
		if(!this.acceptsURL(url)) {
			return null;
		}
		String u = url.substring(5);
		
		if(u.indexOf("|") > 0) {
			u = u.trim();
			Object[] urls = StringUtil.explode(u, "|");
			if(urls == null) {
				throw new SQLException("invalid url");
			}
			Connection[] cons = new Connection[urls.length];
			for(int i = 0; i < urls.length; i++) {
				if(urls[i] == null) {
					if(i > 0) {
						DBUtil.closeAll(cons, i);
					}
					throw new SQLException("invalid url");
				} if(urls[i].toString().equals("")) {
					if(i > 0) {
						DBUtil.closeAll(cons, i);
					}
					throw new SQLException("invalid url");
				} else {
					cons[i] = DriverManager.getConnection(urls[i].toString(), prop);
					if(cons[i] == null) {
						if(i > 0) {
							DBUtil.closeAll(cons, i);
						}
						throw new SQLException("connect fail");
					}
				}
			}
			return new ConnectionImpl(cons);
		} else {
			Connection con = DriverManager.getConnection(url.substring(5).toString(), prop);
			if(con == null) {
				throw new SQLException("connect fail");
			}
			return new ConnectionImpl(con);
		}
	}
	
	public boolean acceptsURL(String url) {
		if(url != null && url.startsWith("xdbc:jdbc:")) {
			try {
				String u = url.substring(5);
				if(u.indexOf("|") > 0) {
					u = u.trim();
					Object[] urls = StringUtil.explode(u, "|");
					if(urls == null) {
						throw new SQLException("invalid url");
					}
					for(int i = 0; i < urls.length; i++) {
						Driver d = DriverManager.getDriver(urls[i].toString());
						if(d == null || !d.acceptsURL(urls[i].toString())) {
							return false;
						}
					}
					return true;
				} else {
					Driver d = DriverManager.getDriver(url.substring(5));
					if(d != null) {
						return d.acceptsURL(url.substring(5));
					}
				}
			} catch(SQLException e) {
				return false;
			}
		}
		return false;
	}
	
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties prop) {
		if(url != null && url.startsWith("xdbc:jdbc:")) {
			try {
				Driver d = DriverManager.getDriver(url.substring(5));
				if(d != null) {
					return d.getPropertyInfo(url.substring(5), prop);
				}
			} catch(SQLException e) {
				return null;
			}
		}
		return null;
	}
	public Logger getParentLogger() {
		return this.logger;
	}
	public boolean jdbcCompliant() {
		return false;
	}
	public int getMajorVersion() {
		return 0;
	}
	public int getMinorVersion() {
		return 5;
	}
}


