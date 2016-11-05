package com.justfun.concurrent.basic;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {
	
	static ConnectionPool pool = new ConnectionPool(10);
	static CountDownLatch start = new CountDownLatch(1);
	static CountDownLatch end;
	
	public static void main(String[] args) throws InterruptedException {
		int threadCount = 10;
		end = new CountDownLatch(threadCount);
		int count = 20;
		AtomicInteger got = new AtomicInteger();
		AtomicInteger notGot = new AtomicInteger();
		for (int i = 0; i < threadCount; i ++) {
			Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "Connection");
			thread.start();
		}
		start.countDown();
		
		//所有的ConnectionRunner先一直执行，执行完毕的就阻塞
		//直到runner里面end.countDown()，所有的ConnectionRunner都执行完毕
		end.await();
		System.out.println("total invoke: " + (threadCount * count));
		System.out.println("get connectioin: " + got);
		System.out.println("not get connectioin: " + notGot);
	}
	
	static class ConnectionRunner implements Runnable {
		int count;
		AtomicInteger got;
		AtomicInteger notGot;
		
		public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
			this.count = count;
			this.got = got;
			this.notGot = notGot;
		}
		
		@Override
		public void run() {
			try {
				// await方法让 ConnectionRunner 阻塞到这里，先不执行run方法
				// 直到start.countDown()执行，所有的ConnectionRunner再执行
				start.await();
			} catch (Exception ex) {}
			while(count > 0) {
				try {
					Connection conn = pool.fetchConnection(1000);
					if (conn != null) {
						try {
							conn.createStatement();
							conn.commit();
						} finally {
							pool.releaseConnection(conn);
							got.incrementAndGet();
						}
					} else {
						notGot.incrementAndGet();
					}
				} catch (Exception ex) {
				} finally {
					count --;
				}
			}
			end.countDown();
		}
	}
}
