package com.justfun.concurrent;

public class Info {
	private String name = "name";
	private String content = "content";
	private volatile boolean flag = true;

	public synchronized void set(String name, String content) {
		while (!flag) {
			try {
				System.out.println( Thread.currentThread().getName() + " set  wait ...." + name);
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.setName(name);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.setContent(content);
		flag = false;
		super.notify();
	}

	public synchronized void get() {
		while (flag) {
			try {
				System.out.println( Thread.currentThread().getName() + " get  wait ....");
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this.getName() + " --> " + this.getContent());
		flag = true;
		super.notifyAll();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return this.name;
	}

	public String getContent() {
		return this.content;
	}
}
