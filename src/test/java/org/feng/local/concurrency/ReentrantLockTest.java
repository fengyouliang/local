package org.feng.local.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {


    @Test
    public void testCollectionEqual() {

        ReentrantLock lock = new ReentrantLock();

        Runnable lockRunnable = new Runnable() {
            @Override
            public void run() {
                lock.lock();
            }
        };

        new Thread(lockRunnable).start();
        new Thread(lockRunnable).start();
    }
}
