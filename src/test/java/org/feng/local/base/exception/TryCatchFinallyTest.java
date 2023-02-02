package org.feng.local.base.exception;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class TryCatchFinallyTest {

    @Test
    public void test_finally_flow() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        test_finally_flow(latch);
        latch.await();
        System.out.println("done");
    }

    private void test_finally_flow(CountDownLatch latch) {
        try {
            System.out.println("latch = " + latch);
            // Lists.newArrayList().get(1);
            return;
        } catch (Throwable throwable) {
            System.out.println("throwable = " + throwable);
            return;
        } finally {
            latch.countDown();
        }
    }

    @Test
    public void test() {
        int i = test1();
        System.out.println("i = " + i);
    }

    private int test1() {

        int a = 1;
        try {
            int b = 1 / 0;
            return a;
        } catch (Exception e) {
            System.out.println("catch:" + a);
            return a;
        } finally {
            ++a;
        }
    }


}
