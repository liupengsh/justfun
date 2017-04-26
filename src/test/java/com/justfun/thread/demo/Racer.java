package com.justfun.thread.demo;

import java.util.concurrent.CopyOnWriteArrayList;

public class Racer extends Thread{
	
	FireFlag fireFlag;
	
	public Racer(FireFlag fireFlag) {
		this.fireFlag = fireFlag;
	}
	
	@Override
	public void run() {
		try {
			this.fireFlag.waitForFire();
			System.out.println("start run " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
	    int num = 10;
	    FireFlag fireFlag = new FireFlag();
	    Thread[] racers = new Thread[num];
	    for (int i = 0; i < num; i++) {
	        racers[i] = new Racer(fireFlag);
	        racers[i].start();
	    }
	    Thread.sleep(1000);
	    fireFlag.fire();
	    
	    CopyOnWriteArrayList<String> lst = new CopyOnWriteArrayList<>();
	}
	
}
