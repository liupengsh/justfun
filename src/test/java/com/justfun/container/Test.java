package com.justfun.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class Test {

	public static void main(String[] args) {
		LinkedList<String> lnkLst = new LinkedList<>();
		ArrayList<String> arrayList = new ArrayList<>();
		
		HashMap<String,String> map = new HashMap<>();
		TreeMap<String,String> treeMap = new TreeMap<>();
		LinkedHashMap<String, String> lnkMap = new LinkedHashMap<>();
		ConcurrentHashMap<String, String> currentMap = new ConcurrentHashMap<>();
		
		HashSet<String> hashSet = new HashSet<>();
		TreeSet<String> treeSet = new TreeSet<>();
		
		CountDownLatch cdl = new CountDownLatch(1);
		
		
		RunnableTest testRun = new RunnableTest();
		testRun.run();
	}
	
	
	static class RunnableTest implements Runnable {
	    private int n = 10;

	    @Override
	    public void run() {

	        while (n > 0) {
	            System.out.println(Thread.currentThread().getName() + "  "
	                    + n--);

	        }
	    }
	}
}
