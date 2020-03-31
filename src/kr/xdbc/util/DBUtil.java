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

package kr.xdbc.util;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataBase Utilities

 * @author HeonJik, KIM
 * @version 0.2
 * @since 0.1
 */

public final class DBUtil {
	public static void closeAll(Connection[] cons, int index) {
		for(int i = (index - 1); i >= 0; i--) {
			try {
				if(cons[i] != null) {
					cons[i].close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}


