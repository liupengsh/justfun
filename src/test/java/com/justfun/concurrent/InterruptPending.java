package com.justfun.concurrent;

public class InterruptPending{
	public static void main(String[] args) {
		Thread.currentThread().interrupt();
		
		long startTime = System.currentTimeMillis();
		try {
			Thread.sleep(2000);
			System.out.println("was NOT interrupted");
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("was interrupted");
		}
		
		//计算中间代码执行的时间
		System.out.println("elapsedTime=" + ( System.currentTimeMillis() - startTime));
	}
}
