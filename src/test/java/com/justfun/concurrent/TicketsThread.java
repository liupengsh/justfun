package com.justfun.concurrent;

public class TicketsThread extends Thread {
	private int ticket = 10;

	public void run() {
		while (true) {
			if (ticket > 0) {
				System.out.println(Thread.currentThread().getName() + "is saling ticket" + ticket--);
			}
		}
	}
}
