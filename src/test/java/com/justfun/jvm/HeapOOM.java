package com.justfun.jvm;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
	static class OOMObject{}
	private static final int _1M = 1024 * 1024;
	
	public static void testOOM() {
		List<OOMObject> list = new ArrayList<>();
		while (true) {
			list.add(new OOMObject());
		}
	}
	
	@SuppressWarnings("unused")
	public static void testAllocation() {
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2*_1M];
		allocation2 = new byte[2*_1M];
		allocation3 = new byte[2*_1M];
		allocation4 = new byte[4*_1M];
	}
	
	public static void main(String[] args) {
		testAllocation();
	}
}
