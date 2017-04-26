package com.justfun.thread.demo;

public class MyLatchWorker extends Thread {
	
	private MyLatch myLatch;
	
	public MyLatchWorker(MyLatch myLatch) {
		this.myLatch = myLatch;
	}
	
	@Override
	public void run() {
		System.out.println("end do" + Thread.currentThread().getName());
		myLatch.countDown();
	}
	
	public static void main(String[] args) throws InterruptedException {
		int workerNums = 10;
		MyLatch latch = new MyLatch(workerNums);
		
		for (int i = 0; i < workerNums; i++) {
			MyLatchWorker worker = new MyLatchWorker(latch);
			worker.start();
		}
		
		latch.await();
		System.out.println("..... all end do");
	}
}
