package com.justfun.thread.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class TestNumberAndCharacter {
	
	// 数字自增
	private volatile AtomicInteger initNum = new AtomicInteger(0);

	// 字母循环集合
	private String[] characterArr = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l" };
	private volatile AtomicInteger characterIdx = new AtomicInteger(0);

	// 读写标志
	private volatile Boolean doingNumer = false;

	public class NumberThread implements Runnable {
		private Object lock;

		public NumberThread(Object lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (lock) {
					while (!doingNumer) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					int result = initNum.incrementAndGet();
					System.out.println(result);
					doingNumer = false;
					lock.notifyAll();
				}
			}
		}

	}

	public class CharacterThread implements Runnable {
		private Object lock;

		public CharacterThread(Object lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (lock) {
					while (doingNumer) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					String result = characterArr[characterIdx.get()];
					int now = characterIdx.incrementAndGet();
					if (now >= characterArr.length) {
						characterIdx.set(0);
					}
					System.out.println(result);
					doingNumer = true;
					lock.notifyAll();
				}
			}
		}
	}

	public static void main(String[] args) {
		TestNumberAndCharacter t = new TestNumberAndCharacter();
		Thread th1 = new Thread(t.new NumberThread(t));
		Thread th2 = new Thread(t.new CharacterThread(t));
		th1.start();
		th2.start();
	}
}
