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

package kr.xdbc.servlet;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet Context 가 초기화 되면, ConnectionManager 를 시작시킨다.
 * web.xml 에 다음을 추가한다.
 * <pre>
 * <listener>
 * <listener-class>kr.xdbc.servlet.ConnectionManagerListener</listener-class>
 * </listener>
 * </pre>
 * @author HeonJik, KIM
 * @version 0.3
 * @since 0.3
 */

public class ConnectionManagerListener implements ServletContextListener {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	public void contextInitialized(ServletContextEvent sce) {
		if(!kr.xdbc.trace.ConnectionManager.getInstance().is()) {
			if(logger.isLoggable(Level.FINER)) { logger.finer("kr.xdbc.trace.ConnectionManager now start"); }
			kr.xdbc.trace.ConnectionManager.getInstance().start();
		} else {
			if(logger.isLoggable(Level.FINER)) { logger.finer("kr.xdbc.trace.ConnectionManager already started"); }
		}
	}
	public void contextDestroyed(ServletContextEvent sce) {
	}
}