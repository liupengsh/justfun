package com.justfun.thread.synchronize;

/**
 * 让线程中断执行，分两种情况：
 * 1.当前线程没有调用阻塞方法：
 * 	   首先设置volatile标志字段，然后因为线程没有阻塞，则当前线程得到cpu后
 *     即可判断是否需要停止执行。没有调用阻塞方法，通过标志字段停止线程执行即可。
 *     
 * 2.当前线程调用了阻塞方法：
 *     如果调用阻塞方法，则当前线程执行方法也即为阻塞方法。
 *     当前线程阻塞于阻塞方法的时候，未获取cpu时间片，不能通过判断标志位进行中断
 *     需要通过调用interrupt方法，让线程中断，提前返回，不再阻塞在阻塞方法上。
 *     但是，这样需要抛出InterruptedException中断异常，告知当前线程已经不再阻塞，
 *     而且可能会有不可预知的异常情况发生，告知当前线程处理这种异常。
 *     
 *     a.如果当前线程需要处理中断异常，则进行处理；
 *     b.如果当前线程是在Runnable的run方法中，则需要调用Thread.currentThread().interrupt()
 *     将当前线程的中断标志重新设置为true，这样可以方便上层调用者对该线程的中断响应作出判断。
 *     
 * 疑问：既然阻塞于阻塞方法的时候，未获取cpu时间片，调用interrupt
 *      又是如何将线程中断的呢？
 *
 */
public class ThreadStop implements Runnable {
	
	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				System.out.println("1111");
				Thread.sleep(2000);
				System.out.println("22222");
			}
		} catch (InterruptedException e) {
			System.out.println("33333:" + Thread.currentThread().getState().toString());
			System.out.println("44444:" + Thread.currentThread().isInterrupted());
			
			Thread.currentThread().interrupt(); // Here!
			
			System.out.println("55555:" + Thread.currentThread().isInterrupted());
			
			return;
		}
	}
	
	public static void main(String[] args) {
		ThreadStop stop = new ThreadStop();
		Thread t = new Thread(stop);
		t.start();
		
		System.out.println("1 state:" + t.getState().toString());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("2 state:" + t.getState().toString());
		
		t.interrupt();
		
		System.out.println("3 state:" + t.getState().toString());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("4 state:" + t.getState().toString());
	}
	
}
