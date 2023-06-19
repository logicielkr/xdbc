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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * String Utilities

 * @author HeonJik, KIM
 * @version 0.3
 * @since 0.1
 */

public final class StringUtil {
/**
 * 문자열을 구분자로 잘라내서 배열로 반환한다.
 * StringTokenizer 로 잘라내서 배열로 만든다.
 * String.split 과의 차이 : 비어있는 항목이 없다.
 * @text 문자열
 * @token 구분자
 */
	public static String[] explode(String text, String token) {
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
/**
 * 문자열 배열에서 주어진 순번의 항목을 반환한다.
 * 유효하지 않는 경우(배열이 null 이거나 가져올 순번이 배열의 크기보다 크거나, 항목의 값이 null 인 경우) 빈 분자열("")을 반환한다.
 * @text 문자열 배열
 * @N 가져올 순번
 */
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
/**
 * BIND 변수가 치환된 SQL을 반환한다.
 * @since 0.3
 */
	public static String getQueryString(String sql, ArrayList al, HashMap hm) {
		StringBuffer sb = new StringBuffer();
		char[] array = sql.toCharArray();
		char prev = 0;
		boolean quote = false;
		boolean comment = false;
		int index = 1;
		char p = 0;
		char c = 0;
		for(int i = 0; i < array.length; i++) {
			switch(array[i]) {
				case '?':
					prev = array[i];
					if(!quote && !comment) {
						if(index <= al.size()) {
							sb.append(al.get(index));
						} else {
							sb.append(array[i]);
						}
						index++;
					} else {
						sb.append(array[i]);
					}
					break;
				case ':':
					prev = array[i];
					if(!quote && !comment && hm != null) {
						StringBuffer val = new StringBuffer();
						i++;
						while(i < array.length) {
							if(
								(array[i] >= 'a' && array[i] <= 'z') ||
								(array[i] >= 'A' && array[i] <= 'Z') ||
								(array[i] >= '0' && array[i] <= '9') ||
								array[i] == '_'
							) {
								val.append(array[i]);
							} else {
								i--;
								break;
							}
							i++;
						}
						String key = val.toString();
						if(!key.equals("") && hm.containsKey(key)) {
							sb.append(hm.get(key));
						} else {
							sb.append(':');
							sb.append(key);
						}
					} else {
						sb.append(array[i]);
					}
					break;
				case '\'':
					if(!comment && !quote) {
						p = array[i];
						quote = !quote;
					} else if(!comment && p == array[i]){
						if(i <= array.length - 2 && array[i + 1] == p) {
							sb.append(array[i]);
							i++;
						} else {
							quote = !quote;
						}
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				case '"':
					if(!comment && !quote) {
						p = array[i];
						quote = !quote;
					} else if(!comment && prev != '\\' && p == array[i]){
						quote = !quote;
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				case '/':
					if(comment && prev == '*' && c == '*') {
						comment = !comment;
					} else if(!quote && !comment && prev == '/') {
						c = array[i];
						comment = !comment;
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				case '*':
					if(!quote && !comment && prev == '/') {
						c = array[i];
						comment = !comment;
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				case '\n':
					if(comment && c == '/') {
						comment = !comment;
					}
					sb.append(array[i]);
					prev = array[i];
					break;
				default :
					sb.append(array[i]);
					prev = array[i];
			}
		}
		return sb.toString();
	}
}


