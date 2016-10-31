package com.justfun.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TicketsCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		return fibc(20);
	}

	static int fibc(int num) {
		if (num == 0) {
			return 0;
		}
		if (num == 1) {
			return 1;
		}
		return fibc(num - 1) + fibc(num - 2);
	}
	
	public static void main(String[] args) {
		try {
			FutureTask<Integer> futureTask = new FutureTask<Integer>(
					new Callable<Integer>() {
						@Override
						public Integer call() throws Exception {
							return fibc(20);
						}
					});
			new Thread(futureTask).start();
			System.out.println("future result from futureTask : "+ futureTask.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
