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

/**
 * String Utilities

 * @author HeonJik, KIM
 * @version 0.2
 * @since 0.1
 */

public final class StringUtil {
	public static String[] explode(String text,String token) {
		if(text == null) {
			return null;
		}
		java.util.ArrayList al = new java.util.ArrayList();
		java.util.StringTokenizer st = null;
		st = new java.util.StringTokenizer(text, token);
		while(st.hasMoreTokens()) {
			al.add((st.nextToken()).toString());
		}
		return (String[])al.toArray(new String[0]);
	}
	public static String gets(Object[] text, int N) {
		String value = "";
		if(text != null && text.length > N) {
			if(text[N] == null) {
				return value;
			} else if(text[N].toString().equals("")) {
				return value;
			} else {
				return text[N].toString();
			}
		} else {
			return value;
		}
	}
}


