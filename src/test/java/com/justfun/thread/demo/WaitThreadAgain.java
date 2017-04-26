package com.justfun.thread.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitThreadAgain extends Thread {
    private volatile boolean fire = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            try {
                while (!fire) {
                    condition.await();
                }
            } finally {
                lock.unlock();
            }
            System.out.println("fired");
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    public void fire() {
        lock.lock();
        try {
            this.fire = true;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
    	WaitThreadAgain waitThread = new WaitThreadAgain();
        waitThread.start();
        Thread.sleep(1000);
        System.out.println("fire");
        waitThread.fire();
    }
}
