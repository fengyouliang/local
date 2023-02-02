package org.feng.local.concurrency;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

public class ThreadLocalExample implements Runnable {

    // SimpleDateFormat 不是线程安全的，所以每个线程都要有自己独立的副本
    private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalExample obj = new ThreadLocalExample();
        List<Thread> threadList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(obj, "" + i);
            threadList.add(t);
        }
        System.out.println("After thread start");
        for (Thread thread : threadList) {
            Thread.sleep(new Random().nextInt(1000));
            thread.start();
        }

        System.out.println("After thread start");
        System.gc();
        Thread.sleep(1000);
        System.out.println("After thread start");
    }

    @Override
    public void run() {
        System.out.println("Thread Name= " + Thread.currentThread().getName() + " default Formatter = " + formatter.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // formatter pattern is changed here by thread, but it won't reflect to other threads
        formatter.set(new SimpleDateFormat());

        System.out.println("Thread Name= " + Thread.currentThread().getName() + " formatter = " + formatter.get().toPattern());
    }

}
