package com.justfun.concurrent.basic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class RaceAndRecordAgain implements Runnable {
	Fire fire;
	CountDownLatch countDown;
	static Map<String, String> records = new ConcurrentHashMap<>();
	
	public RaceAndRecordAgain(Fire fire, CountDownLatch countDown) {
		this.fire = fire;
		this.countDown = countDown;
	}
	
	@Override
	public void run() {
		try {
			fire.waitFire();
			Thread.sleep(new Random().nextInt(20) * 1000);
			
			String racerName = Thread.currentThread().getName();
			System.out.println(racerName + "到达终点");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			records.put(racerName, sf.format(new Date()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		countDown.countDown();
	}
	
	public static void showRaceRecords() {
		for (String key : records.keySet()) {
			System.out.println(key + ",结束时间:" + records.get(key));
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		int raceNum = 10;
		Fire fire = new Fire();
		CountDownLatch countDown = new CountDownLatch(raceNum);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Thread[] racers = new Thread[10];
		for (int i = 0; i < raceNum; i++) {
			racers[i] = new Thread(new RaceAndRecordAgain(fire, countDown));
			racers[i].start();
		}
		
		Thread.sleep(100);
		
		System.out.println("比赛开始:" + sf.format(new Date()));
		fire.fire();
		countDown.await();
		System.out.println("比赛结束:" + sf.format(new Date()));
		RaceAndRecordAgain.showRaceRecords();
	}
	
	public static class Fire {
		private volatile boolean fire = false;
		Object obj = new Object();
		public void waitFire() throws InterruptedException {
			synchronized (obj) {
				while (!fire) {
					obj.wait();
				}
			}
		}
		public synchronized void fire() {
			synchronized (obj) {
				fire = true;
				obj.notifyAll();
			}
		}
	}
}
