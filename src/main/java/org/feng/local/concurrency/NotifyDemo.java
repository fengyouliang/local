package org.feng.local.concurrency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotifyDemo {

    final Object lock = new Object();

    private static void sleep(long sleepVal) {
        try {
            Thread.sleep(sleepVal);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        new NotifyDemo().startThreadA();
    }

    public void startThreadA() {
        new Thread(() -> {
            synchronized (lock) {
                log.info("A get lock");
                startThreadB();
                log.info("A start wait");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }

                log.info("A get lock after wait");
                log.info("A release lock");
            }
        }, "Thread-A").start();
    }

    public void startThreadB() {
        new Thread(() -> {
            synchronized (lock) {
                log.info("B get lock");
                startThreadC();
                sleep(100);
                log.info("B start notify");
                lock.notify();
                log.info("B release lock");

            }
        }, "Thread-B").start();
    }

    public void startThreadC() {
        new Thread(() -> {
            synchronized (lock) {
                log.info("C get lock");
                log.info("C release lock");
            }
        }, "Thread-C").start();
    }
}
