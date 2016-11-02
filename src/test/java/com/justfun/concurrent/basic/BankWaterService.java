package com.justfun.concurrent.basic;

import java.util.Map.Entry;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BankWaterService implements Runnable {
	
	private CyclicBarrier c = new CyclicBarrier(4, this);
	
	private Executor executor = Executors.newFixedThreadPool(4);
	
	private ConcurrentHashMap<String, Integer> sheetWaterCount = new ConcurrentHashMap<>();
	
	private void count() {
		for (int i = 0; i < 4; i ++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					sheetWaterCount.put(Thread.currentThread().getName(), 1);
					try {
						c.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	@Override
	public void run() {
		int result = 0 ;
		for (Entry<String,Integer> sheet : sheetWaterCount.entrySet()) {
			result += sheet.getValue();
		}
		sheetWaterCount.put("result", result);
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		BankWaterService service = new BankWaterService();
		service.count();
	}

}
