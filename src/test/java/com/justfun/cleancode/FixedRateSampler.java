package com.justfun.cleancode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedRateSampler {
    private final AtomicInteger counter = new AtomicInteger();
    private final int rate;
    public FixedRateSampler(int rate) {
        this.rate = rate;
    }
    public boolean hit() {
        int count = counter.incrementAndGet();
        if (count >= rate) {
            synchronized (counter) {
                if (counter.get() >= rate) {
                    counter.set(0);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
    	FixedRateSampler sampler = new FixedRateSampler(2);
    	ExecutorService threadpool = Executors.newScheduledThreadPool(100);
    	
    	for (int i = 0; i < 3; i++) {
    	    threadpool.submit(new Runnable() {
    	        public void run() {
    	            for (int j = 0; j < 4; j++) {
    	                if (sampler.hit()) {
    	                    System.out.println("hit");
    	                }
    	            }
    	        }
    	    });
    	}
    	threadpool.shutdown();
    }
}
