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
		
		int[] f = {6,1,2,7,9,3,4,5,10,8};
		int[] tmp = new int[f.length];
		sort.mergeSort(f, 0, f.length-1, tmp);
		System.out.println(Arrays.toString(f));
		
		int[] g = {6,1,2,7,9,3,4,5,10,8};
		sort.heapSort(g);
		System.out.println(Arrays.toString(g));
	}
	
	/**
	 * 冒泡排序
	 * 从第一个元素开始，依次和后一个元素进行比较，如果比后一个大，则交换
	 * 从第一个到最后一个，一轮比较之后，最大值冒泡到最后一个
	 * 第一轮比较之后，第二轮开始，只需要比较到N－1个值，因为第N个值已经是最大
	 * 因此，外面的循环是从N-1开始，到 1 结束，等0的时候只有一个数a[0]，无需比较
	 * 里面的排序从0开始，到外层循环的 j 结束
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
	 * 从数组的第二个元素开始（第一个元素默认为已排序），两两比较，
	 * 如果后一个比前面数据小，则进行更换，执行插入
	 * 可以理解为从i开始，两两比较大小，将小的插入到大的前面
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
	
	/**
	 * 归并排序
	 * 归并排序采用分治的思想，将一个数组不断的进行拆分，
	 * 然后将拆分到最小的数组，进行数值合并，合并到临时数组
	 */
	public void mergeSort(int[] a, int first, int last, int[] tmp) {
		if (first < last) {
			int mid = (first + last)/2;
			mergeSort(a, first, mid, tmp);
			mergeSort(a, mid + 1, last, tmp);
			mergeArray(a, first, mid, last, tmp);
		}
	}
	
	private void mergeArray(int[] a, int first, int mid, int last, int[] c) {
		int i = first, j = mid + 1;
		int m = mid, n = last;
		int k = 0;
		while (i <= m && j <= n) {
			if (a[i] <= a[j]) {
				c[k++] = a[i++];
			} else {
				c[k++] = a[j++];
			}
		}
		while (i <= m) {
			c[k++] = a[i++];
		}
		while (j <= n) {
			c[k++] = a[j++];
		}
		for (i = 0; i < k; i++) {
			a[first + i] = c[i];
		}
	}
	
	public void heapSort(int[] a) {
		int N = a.length - 1;
		/**
		 * k=N/2; 就能顾及到2k和2k＋1的元素
		 * 在下沉过程中，小的下沉，大的就上浮
		 * 所以k＝1时，下沉后，根节点就是最大的
		 * 
		 * 6,1,2,7,9, -- 3,4,5,10,8
		 *********  sink(a, 4);  
		 * 6,1,2,7,10, -- 3,4,5,9,8  k=4 j=8   (a[j8]=10 > a[j9] j = 8)  a[k4]=9 < a[j]=40 swap
		 *********  sink(a, 3);
		 * 6,1,2,7,10, -- 3,4,5,9,8  k=3 j=6   a[j6]=4 < [j+1]=5  j++ = 7 a[k4]=10 > a[j7] break 
		 *********  sink(a, 2);
		 * 6,1,10,7,2, -- 3,4,5,9,8 ( k=2 j=4  a[j4]=10 > [j+1]=3       swap a[2] a[4])
		 * 6,1,10,7,9, -- 3,4,5,2,8   k=4 j=8  a[j8]=9 > a[j+1]=8 j=8  a[k4]=2 < a[j8]=9 swap
		 *********  sink(a, 1);
		 * 6,10,1,7,9, -- 3,4,5,2,8  k=1 j=2     swap
		 * 6,10,9,7,1, -- 3,4,5,2,8  k=2 j=4     swap
		 * 6,10,9,7,8, -- 3,4,5,2,1  k=4 j=8     swap
		 */
		for (int k = N/2; k >= 1; k--) {
			sink(a, k, N);
		}
		System.out.println("***************" + Arrays.toString(a));
		/* 
         * 根节点最大的，和尾结点交换后，此时尾结点就是最大的，N--，尾结点就不需要动了，以此类推 
         */  
        while(N>1){  
        	/**
        	 * 1. 交换1和N＝9, N-- = 8
        	 *    从1开始sink，长度限定为8
        	 *    
        	 * 第一个不断的进行和最后一个的sink
        	 */
            exch(a, 1, N--);
            System.out.println("        交换后：" + Arrays.toString(a));
            sink(a, 1, N); 
            System.out.println("=========" + Arrays.toString(a));
        }
	}
	
	private void exch(int[] pq,int i, int j) {  
        int t = pq[i];  
        pq[i] = pq[j];  
        pq[j] = t;  
    }
	
	private void sink(int[] a, int k, int N) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && a[j] < a[j+1]) {
				j++;
			}
			if (a[k] >= a[j]) {
				break;
			}
			int tmp = a[k];
			a[k] = a[j];
			a[j] = tmp;
			k = j;
		}
	}
}
