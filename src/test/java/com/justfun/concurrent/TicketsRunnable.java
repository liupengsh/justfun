package com.justfun.concurrent;

public class TicketsRunnable implements Runnable {

	private int tickets = 10;
	
	@Override
	public void run() {
		while(true) {
			if (tickets > 0) {
				System.out.println(Thread.currentThread().getName() + " is saling ticket " + tickets--);
			}
		}
	}
	
	public static void main(String[] args) {
		TicketsRunnable tr = new TicketsRunnable();
		new Thread(tr).start();
		new Thread(tr).start();
		new Thread(new TicketsRunnable()).start();
	}

}
