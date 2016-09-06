package com.justfun.concurrent;

public class InfoConsumer implements Runnable {
	private Info info = null;

	public InfoConsumer(Info info) {
		this.info = info;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			this.info.get();
		}
	}
}
