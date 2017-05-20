package com.justfun.thread.synchronize;

public class ThreadState {
	
	private static Thread stateThread;
//	private static volatile boolean KEEP_RUNNING = true;
	
	public static void main(String[] args) {
		stateThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(Thread.interrupted()) {
					try {
						System.out.println("sleep...");
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					for (int i = 1; i <= 5; i++) {
						System.out.println(i + "==>" + "the thread is running...");
					}
				}
			}
		});
		stateThread.start();
		
		try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		System.out.println("we gonna stop sThread...");
		stateThread.interrupt();
//		KEEP_RUNNING = false;
		
		System.out.println("==>" + stateThread.getState().toString());
	}
}
