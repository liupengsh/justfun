package com.justfun.concurrent.basic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class RaceAndRecord extends Thread {

	FireFlag fireFlag;
	CyclicBarrier c;
	static Map<String, String> records = new ConcurrentHashMap<>();

	public RaceAndRecord(FireFlag fireFlag, CyclicBarrier c) {
		this.fireFlag = fireFlag;
		this.c = c;
	}

	@Override
	public void run() {
		String racerName = Thread.currentThread().getName();
		try {
			this.fireFlag.waitForFire();
			Thread.sleep(new Random().nextInt(20) * 1000);

			System.out.println(racerName + "到达终点");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			records.put(racerName, sf.format(new Date()));
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public static void showRaceRecords() {
		for (String key : records.keySet()) {
			System.out.println("参赛人:" + key + ",结束时间:" + records.get(key));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int num = 10;
		FireFlag fireFlag = new FireFlag();
		CyclicBarrier c = new CyclicBarrier(num, new Runnable() {
			@Override
			public void run() {
				System.out.println("最后一位到达终点,赛事结束时间:" + sf.format(new Date()));
				RaceAndRecord.showRaceRecords();
			}
		});
		
		
		Thread[] racers = new Thread[num];
		for (int i = 0; i < num; i++) {
			racers[i] = new RaceAndRecord(fireFlag, c);
			racers[i].start();
		}
		
		
		Thread.sleep(1000);
		System.out.println("赛事开始:" + sf.format(new Date()));
		fireFlag.fire();
	}

}
