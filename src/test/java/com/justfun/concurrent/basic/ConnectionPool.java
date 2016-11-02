package com.justfun.concurrent.basic;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
	private LinkedList<Connection> pool = new LinkedList<Connection>();
	
	public ConnectionPool(int initSize) {
		if (initSize > 0) {
			for (int i = 0; i < initSize; i++) {
				pool.addLast(ConnectionDriver.createConnection());
			}
		}
	}
	
	public void releaseConnection(Connection conn) {
		if (conn != null) {
			synchronized(pool) {
				pool.add(conn);
				pool.notifyAll();
			}
		}
	}
	
	public Connection fetchConnection(long mills) throws InterruptedException {
		synchronized(pool) {
			if (mills < 0) {
				while (pool.isEmpty()) {
					pool.wait();
				}
				return pool.removeFirst();
			} else {
				long future = System.currentTimeMillis() + mills;
				long remaining = mills;
				while(pool.isEmpty() && remaining > 0) {
					pool.wait(remaining);
					remaining = future - System.currentTimeMillis();
				}
				if (!pool.isEmpty()) {
					return pool.removeFirst();
				}
				return null;
			}
		}
	}
}
