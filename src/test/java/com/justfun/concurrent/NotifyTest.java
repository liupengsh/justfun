package com.justfun.concurrent;

public class NotifyTest {
	public static class TestObj {
		public String id;
		public String name;
		public TestObj(String id, String name) {
			this.id = id;
			this.name = name;
		}
	}
	static TestObj obj = new TestObj("1", "name");
	
	public static void main(String[] args) {
		
		Runnable runA = new Runnable() {
			public void run() {
				synchronized (obj) {
					System.out.println(obj.id + "... wait");
					try {
						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println(obj.id + "... wait end");
				}
			}
		};
		Runnable runB = new Runnable() {
			public void run() {
				synchronized (obj) {
					System.out.println(obj.id + "... notify");
//					obj = new TestObj("2", "ddd"); 
					obj.notify();
				}
			}
		};
		
		new Thread(runA).start();
		
		new Thread(runB).start();
	}
}
