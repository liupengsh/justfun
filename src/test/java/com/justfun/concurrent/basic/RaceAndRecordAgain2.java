package com.justfun.concurrent.basic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class RaceAndRecordAgain2 implements Runnable {
	
	Fire fire;
	CyclicBarrier cb;
	static Map<String, String> records = new ConcurrentHashMap<>();
	
	public RaceAndRecordAgain2(Fire fire, CyclicBarrier cb) {
		this.fire = fire;
		this.cb = cb;
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
			
			cb.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public static void showRaceRecords() {
		for (String key : records.keySet()) {
			System.out.println(key + ",结束时间:" + records.get(key));
		}
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
		public void fire() {
			synchronized (obj) {
				fire = true;
				obj.notifyAll();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		int raceNum = 10;
		Fire fire = new Fire();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CyclicBarrier cb = new CyclicBarrier(raceNum, new Runnable() {
			@Override
			public void run() {
				System.out.println("比赛结束:" + sf.format(new Date()));
				RaceAndRecordAgain2.showRaceRecords();
			}
		});
		
		Thread[] racers = new Thread[10];
		for (int i = 0; i < raceNum; i++) {
			racers[i] = new Thread(new RaceAndRecordAgain2(fire, cb));
			racers[i].start();
		}
		
		Thread.sleep(1000);
		
		System.out.println("比赛开始:" + sf.format(new Date()));
		fire.fire();
	}
	
}
