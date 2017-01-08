package com.justfun.http;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpTest {
	
	static CountDownLatch start = new CountDownLatch(1);
	static CountDownLatch end;
	
	public static void main(String[] args) throws InterruptedException {
		int threadCount = 1000;
		end = new CountDownLatch(threadCount);
		AtomicInteger got = new AtomicInteger();
		AtomicInteger notGot = new AtomicInteger();
		for (int i = 0; i < threadCount; i ++) {
			Thread thread = new Thread(new ConnectionRunner(got, notGot), "Connection");
			thread.start();
		}
		start.countDown();
		
		//所有的ConnectionRunner先一直执行，执行完毕的就阻塞
		//直到runner里面end.countDown()，所有的ConnectionRunner都执行完毕
		end.await();
		System.out.println("total invoke: " + threadCount);
		System.out.println("get connectioin: " + got);
		System.out.println("not get connectioin: " + notGot);
	}
	
	static class ConnectionRunner implements Runnable {
		AtomicInteger got;
		AtomicInteger notGot;
		
		public ConnectionRunner(AtomicInteger got, AtomicInteger notGot) {
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
			try {
				int status = MultiThreadHttpClient.getMethod("http://www.baidu.com");
				if (status != 200) {
					notGot.incrementAndGet();
				} else {
					got.incrementAndGet();
				}
			} catch (Exception ex) {
				notGot.incrementAndGet();
			}
			end.countDown();
		}
	}
}
