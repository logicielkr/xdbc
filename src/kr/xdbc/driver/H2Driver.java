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
import kr.xdbc.sql.ConnectionImpl;
import kr.xdbc.util.StringUtil;
import kr.xdbc.util.DBUtil;
import java.util.Properties;
import java.sql.SQLException;

/**
 * H2 Driver Wrapper

 * @author HeonJik, KIM
 * @version 0.2
 * @since 0.1
 */

public class H2Driver extends org.h2.Driver implements java.sql.Driver {
	public static final String PREFIX = "xdbc:jdbc:h2:";
	
	static {
		try {
			DriverManager.registerDriver(new org.h2.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			DriverManager.registerDriver(new kr.xdbc.driver.H2Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	public boolean acceptsURL(java.lang.String url) {
		return super.acceptsURL(url.substring(5));
	}
	
	public java.sql.DriverPropertyInfo[] getPropertyInfo(String url, Properties prop) {
		return super.getPropertyInfo(url.substring(5), prop);
	}
}


