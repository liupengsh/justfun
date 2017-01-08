package com.justfun.concurrent;

public class MyThreadDemo {
	public static void main(String[] args) {
//		new MyThreadExtendThread().start();
//		new MyThreadExtendThread().start();
//		new MyThreadExtendThread().start();
		
		MyThreadImplRunnable my  = new MyThreadImplRunnable();
		new Thread(my).start();
		new Thread(my).start();
		new Thread(my).start();
		new Thread(my).start();
		new Thread(my).start();
	}
}
