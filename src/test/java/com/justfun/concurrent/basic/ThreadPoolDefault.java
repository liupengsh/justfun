package com.justfun.concurrent.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolDefault<Job extends Runnable> implements ThreadPool<Job> {
	
	private static final int MAX_WORKER_NUMBERS = 10;
	private static final int DEFAULT_WORKER_NUMBERS = 5;
	private static final int MIN_WORKER_NUMBERS = 1;
	
	class Worker implements Runnable {
		private volatile boolean running = true;
		public void shutDown() {
			running = false;
		}
		@Override
		public void run() {
			while(running) {
				Job job = null;
				synchronized(jobs) {
					while (jobs.isEmpty()) {
						try {
							jobs.wait();
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt();
							return;
						}
					}
					job = jobs.removeFirst();
				}
				if (job != null) {
					try {
						job.run();
					} catch(Exception ex) {
						
					}
				}
			}
		}
	}
	
	private final LinkedList<Job> jobs = new LinkedList<Job>();
	// 这个地方，如果不使用synchronizedList，会出现什么问题吗？
	private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
	private int workerNum = DEFAULT_WORKER_NUMBERS;
	private AtomicLong threadNum = new AtomicLong();
	
	public ThreadPoolDefault() {
		initializeWorkers(DEFAULT_WORKER_NUMBERS);
	}
	
	public ThreadPoolDefault(int num) {
		workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
		initializeWorkers(workerNum);
	}
	
	private void initializeWorkers(int num) {
		for (int i = 0; i < num; i ++) {
			Worker worker = new Worker();
			workers.add(worker);
			Thread thread = new Thread(worker, "ThreadPool-Worker-" +threadNum.incrementAndGet());
			thread.start();
		}
	}

	@Override
	public void execute(Job job) {
		if (job != null) {
			synchronized(jobs) {
				jobs.addLast(job);
				//这个地方为什么要用notify()而不是notifyAll()？答：避免将等待中的线程全部移到阻塞队列中去
				jobs.notify();
			}
		}
	}

	@Override
	public void shutdown() {
		for (Worker worker : workers) {
			worker.shutDown();
		}
	}

	@Override
	public void addWorkers(int num) {
		synchronized(jobs) {
			if (num + this.workerNum > MAX_WORKER_NUMBERS) {
				num = MAX_WORKER_NUMBERS - this.workerNum;
			}
			initializeWorkers(num);
			this.workerNum += num;
		}
	}

	@Override
	public void removeWorkers(int num) {
		synchronized(jobs) {
			if (num >= this.workerNum) {
				throw new IllegalArgumentException("beyond workNum");
			}
			int count = 0;
			while (count < num) {
				Worker worker = workers.get(count);
				if (workers.remove(worker)) {
					worker.shutDown();
					count++;
				}
			}
			this.workerNum -= count;
		}
	}

	@Override
	public int getJobSize() {
		return jobs.size();
	}

}
