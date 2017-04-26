package com.justfun.thread.demo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TestNumberAndCharacterAgain {
	// 数字自增
	private volatile AtomicInteger initNum = new AtomicInteger(0);

	// 字母循环集合
	private String[] characterArr = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l" };
	private volatile AtomicInteger characterIdx = new AtomicInteger(0);

	private Lock lock = new ReentrantLock();
	private Condition number = lock.newCondition();
	private Condition character = lock.newCondition();
	
	// 读写标志
	private volatile Boolean doingNumer = false;
	private volatile boolean stop = false;

	public class NumberThread implements Runnable {
		@Override
		public void run() {
			while (!stop) {
				try {
					lock.lockInterruptibly();
					while (!doingNumer) {
						number.await();
					}

					int result = initNum.incrementAndGet();
					System.out.println(result);
					doingNumer = false;
					character.signal();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}

	public class CharacterThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					lock.lockInterruptibly();
					while (doingNumer) {
						character.await();
					}
					String result = characterArr[characterIdx.get()];
					int now = characterIdx.incrementAndGet();
					if (now >= characterArr.length) {
						characterIdx.set(0);
						stop = true;
						break;
					}
					System.out.println(result);
					doingNumer = true;
					number.signal();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}

	public static void main(String[] args) {
		TestNumberAndCharacterAgain t = new TestNumberAndCharacterAgain();
		Thread th1 = new Thread(t.new NumberThread());
		Thread th2 = new Thread(t.new CharacterThread());
		th1.start();
		th2.start();
	}
}
