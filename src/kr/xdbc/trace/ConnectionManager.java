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

package kr.xdbc.trace;

import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Collections;
import java.util.Map;

/**
 * xdbc 데이타베이스 연결 관리자

 * @author HeonJik, KIM
 * @version 0.2
 * @since 0.1
 */

public class ConnectionManager {
	private int index;
	private HashMap hmap;
	private Map map;
	private boolean isTrace = false;
	private ConnectionManager() {
		this.index = Integer.MIN_VALUE;
		this.hmap = new HashMap();
		this.map = Collections.synchronizedMap(this.hmap);
	}
	static class ConnectionManagerHolder {
		static ConnectionManager instance = new ConnectionManager();
	}
	public static ConnectionManager getInstance() {
		return ConnectionManagerHolder.instance;
	}
	private synchronized int getNextIndex() {
		if(index >= Integer.MAX_VALUE) {
			index = Integer.MIN_VALUE;
		}
		return this.index++;
	}
	private int count() {
		return this.map.size();
	}
	public void trace(java.io.PrintStream out) {
		Collection col = map.keySet();
		Integer[] list = (Integer[])col.toArray(new Integer[0]);
		if(list != null) {
			for(int x = 0; x < list.length; x++) {
				if(list[x] != null) {
					StackTraceElement[] trace = (StackTraceElement[])this.map.get(list[x]);
					if(trace != null) {
						for(int i = 0; i < trace.length; i++) {
							out.println(trace[i]);
						}
						out.println();
						out.println();
						out.println();
					}
				}
			}
		}
	}
	public void trace(java.io.Writer out) throws java.io.IOException {
		Collection col = map.keySet();
		Integer[] list = (Integer[])col.toArray(new Integer[0]);
		if(list != null) {
			for(int x = 0; x < list.length; x++) {
				if(list[x] != null) {
					StackTraceElement[] trace = (StackTraceElement[])this.map.get(list[x]);
					if(trace != null) {
						for(int i = 0; i < trace.length; i++) {
							out.write(trace[i].toString());
							out.write("\n");
						}
						out.write("\n");
						out.write("\n");
						out.write("\n");
					}
				}
			}
		}
	}
	public void trace() {
		trace(System.out);
	}
	public int mark() {
		if(this.isTrace) {
			return markInternal();
		} else {
			return 0;
		}
	}
	private synchronized int markInternal() {
		int index = getNextIndex();
		mark(index);
		return index;
	}
	private synchronized void mark(int index) {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		this.map.put(Integer.valueOf(index), trace);
	}
	public void clear(int index) {
		if(this.isTrace) {
			clearInternal(index);
		}
	}
	public boolean is() {
		return this.isTrace;
	}
	public void start() {
		this.map.clear();
		this.isTrace = true;
	}
	public void end() {
		this.isTrace = false;
		this.map.clear();
	}
	private synchronized void clearInternal(int index) {
		this.map.remove(Integer.valueOf(index));
	}
}

