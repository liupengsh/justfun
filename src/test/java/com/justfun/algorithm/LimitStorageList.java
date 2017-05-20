package com.justfun.algorithm;

import java.util.List;

public class LimitStorageList
{
    private long maxTopN;// topN最大值
    private List<KeyCountPair> topNList;// 存储topN的list

    public LimitStorageList()
    { }

    public LimitStorageList(long maxTopN, List<KeyCountPair> list)
    {
        this.maxTopN = maxTopN;
        this.topNList = list;
    }

    /**
     * 加入keyCountPair对象
     */
    void put(KeyCountPair keyCountPair)
    {
        // 判断topN有没有达到最大值
        if (topNList.size() < this.maxTopN)// topN没有超过最大值
        {

            int index = isExist(keyCountPair);
            if (index == -1)// 不重复
            {
                topNList.add(keyCountPair);
                insertSort(topNList);
            }
            else
            {
                topNList.remove(keyCountPair);
                topNList.add(keyCountPair);
                insertSort(topNList);
            }
        }
        else
        {
            // 判断加入值和最后一个值的大小
            if (-1 == (keyCountPair.compareTo(topNList.get(topNList.size() - 1))))// 新加入的值大于topNList最后一个值
            {
                // 判断新加入对象和topNList里面有没有重复对象
                int index = isExist(keyCountPair);
                if (index == -1)// 没有重复
                {
                    topNList.set(topNList.size() - 1, keyCountPair);
                    insertSort(topNList);
                }
                else
                {
                    topNList.remove(keyCountPair);
                    topNList.add(keyCountPair);
                    insertSort(topNList);
                }
            }
        }

    }

    /**
     * 插入排序,只对最后一个值排序
     */
    private static void insertSort(List<KeyCountPair> topNList)
    {
        // 排序
        int i = topNList.size() - 1;
        KeyCountPair temp = topNList.get(i);
        int j = i - 1;
        for (; j >= 0 && (temp.compareTo(topNList.get(j)) == -1); j--)
        {
            topNList.set(j + 1, topNList.get(j));

        }
        topNList.set(j + 1, temp);
    }

    /**
     * 判断topNList是否存在新加入的对象
     * @return -1不重复
     */
    int isExist(KeyCountPair keyCountPair)
    {
        KeyCountPair keyCountPairTemp;
        for (int i = 0, length = topNList.size(); i < length; i++)
        {
            keyCountPairTemp = topNList.get(i);
            if (keyCountPairTemp.getKey().equals(keyCountPair.getKey()))
            {
                return i;
            }
        }
        return -1;
    }
    
    static class KeyCountPair implements Comparable<KeyCountPair> {
    	private String key;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		@Override
		public int compareTo(KeyCountPair p) {
			return this.key.compareTo(p.getKey());
		}
    }
}
