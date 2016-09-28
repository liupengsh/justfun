package com.justfun.algorithm;

import java.util.Arrays;

public class Sorts {
	
	public static void main(String[] args) {
		int[] a = {15,6,1,2,7,9,3,4,5,10,8,11,14,13,12,18,0};
		Sorts sort = new Sorts();
		sort.bubbleSort(a);
		System.out.println(Arrays.toString(a));
		
		int[] b = {15,6,1,2,7,9,3,4,5,10,8,11};
		sort.quickSort(b, 0, b.length - 1);
		System.out.println(Arrays.toString(b));
		
		int[] c = {15,6,1,2,7,9,3,4,5,10,8,11};
		sort.chooseSort(c);
		System.out.println(Arrays.toString(c));
		
		int[] d = {15,6,1,2,7,9,3,4,5,10,8,11};
		sort.insertSort(d);
		System.out.println(Arrays.toString(d));
		
		int[] e = {15,6,1,2,7,9,3,4,5,10,8,11,14,13,12,18,0};
		sort.shellSort(e);
		System.out.println(Arrays.toString(e));
	}
	
	/**
	 * 冒泡排序
	 * 从第一个元素开始，依次和后一个元素进行比较，如果比后一个大，则交换
	 * 从第一个到最后一个，一轮比较之后，最大值冒泡到最后一个
	 * 第一轮比较之后，第二轮开始，只需要比较到N－1个值，因为第N个值已经是最大
	 * @param a
	 */
	public void bubbleSort(int[] a) {
        for (int i = a.length - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if (a[j + 1] < a[j]){
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }
	/**
	 * 快速排序
	 * 快速排序是非常经典第一个排序方法，它定义来一个标兵值，然后从2端开始，分别和标兵值进行对比
	 * 先从右边开始，如果比标兵值小，则停下
	 * 然后从左边开始，如果比标兵值大，则停下，然后交换左右两边第数据
	 * 以上，一直执行到左右两边相等
	 * 之后，左边的都比标兵小，右边第都比标兵大
	 * 最后，分段，重复执行
	 * @param a
	 */
	public void quickSort(int[] a, int lo, int hi) {
		if(lo>=hi){
			return;
		}
		int base = a[lo];
		int i = lo;
		int j = hi;
		while (i != j) {
			while (a[j] >= base && i < j) {
				j--;
			}
			while (a[i] <= base && i < j) {
				i++;
			}
			if (i < j) {
				int tmp = a[i];
				a[i] = a[j];
				a[j] = tmp;
			}
		}
		a[lo] = a[i];
		a[i] = base;
		quickSort(a, lo, i - 1);
		quickSort(a, i + 1, hi);
	}
	/**
	 * 选择排序
	 * 从第一个元素开始
	 * @param a
	 */
	public void chooseSort(int[] a) {
		for (int i = 0; i < a.length; i++) {
			int base = i;
			int min = a[i];
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < min) {
					base = j;
					min = a[j];
				}
			}
			a[base] = a[i];
			a[i] = min;
		}
	}
	/**
	 * 插入排序
	 * 从数组的第二个元素开始（第一个元素默认为已排序），依次和前面数据进行对比
	 * 如果比前面数据小，则进行更换，执行插入
	 * @param a
	 */
	public void insertSort(int[] a) {
		for (int i = 1; i < a.length; i++) {
			for (int j = i; j >0 && a[j] < a[j - 1]; j--) {
				int tmp = a[j];
				a[j] = a[j - 1];
				a[j-1] = tmp;
			}
		}
	}
	/**
	 * 希尔排序
	 * 希尔排序是插入排序的升级版。插入排序进行一个一个的数据比较，循环次数较多
	 * 可以使用一定第step进行分组，每组比较完毕之后，减少步长，继续比较
	 * 这样一直执行到步长为1
	 * 原理是：基于基本有序的数据，插入排序会减少比较次数
	 * {15,6,1,2,7,9,3,4,5,10,8,11,14,13,12,18,0};
	 */
	public void shellSort(int[] a) {
		int step = 1;
		int len = a.length;
		//动态计算步长
		while(step < len/3) {
			step = 3*step + 1;
		};
		//步长不断减小，直到为1，因此最外层循环为步长循环
		while (step >= 1) {
			for (int i = step; i < len; i++) {
				for (int j = i; j - step >= 0 && a[j] < a[j-step]; j = j - step  ) {
					int tmp = a[j];
					a[j] = a[j-step];
					a[j-step] = tmp;
				}
			}
			step = step/3;
		}
	}
}
