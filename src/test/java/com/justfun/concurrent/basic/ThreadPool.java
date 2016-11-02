package com.justfun.concurrent.basic;

public interface ThreadPool<Job extends Runnable> {
	
	void execute(Job job);
	
	void shutdown();
	
	void addWorkers(int num);
	
	void removeWorkers(int num);
	
	int getJobSize();
}
