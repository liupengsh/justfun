package com.justfun.concurrent;

public class InfoDemo {
	public static void main(String args[]) {
		Info info = new Info();
		InfoProducer pro = new InfoProducer(info);
		InfoConsumer con = new InfoConsumer(info);
		new Thread(pro, "product a :").start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(con, "con :").start();
	}
}
