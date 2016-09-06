package com.justfun.concurrent;

public class InfoProducer implements Runnable {
	private Info info = null;

	public InfoProducer(Info info) {
		this.info = info;
	}

	public void run() {
		boolean flag = true;
		for (int i = 0; i < 10; i++) {
			System.out.println( Thread.currentThread().getName() + " run  " + flag);
			if (flag) {
				this.info.set("姓名--1", "内容--1");
				flag = false;
			} else {
				this.info.set("姓名--2", "内容--2");
				flag = true;
			}
		}
	}
}
