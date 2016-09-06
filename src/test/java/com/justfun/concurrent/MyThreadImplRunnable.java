package com.justfun.concurrent;

public class MyThreadImplRunnable implements Runnable {

	private int ticket = 5;
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			if (ticket > 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("run: " + Thread.currentThread().getName() +  " ticket = " + ticket--);
			}
		}
	}

}
