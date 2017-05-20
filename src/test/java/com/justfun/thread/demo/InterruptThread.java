package com.justfun.thread.demo;

public class InterruptThread {
	
	public static void main(String[] args) {
		Thread loop = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (Thread.interrupted()) {
						System.out.println("xxx");
						break;
					}
				}
				System.out.println("yyy");
			}
		});
		
		loop.start();
		loop.interrupt();
		
		System.out.println("zzz");
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
	
}
