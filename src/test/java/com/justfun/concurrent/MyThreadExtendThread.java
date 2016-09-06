package com.justfun.concurrent;

public class MyThreadExtendThread extends Thread {
	
	private int ticket = 5;
	
	public void run() {
		for (int i = 0; i < 10; i ++) {
			if (ticket > 0) {
				System.out.println(this.getName() + " ticket = " + ticket--);
			}
		}
	}
}
