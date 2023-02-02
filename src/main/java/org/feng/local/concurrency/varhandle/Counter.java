package org.feng.local.concurrency.varhandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Counter {
    private static final VarHandle COUNT;

    static {
        try {
            // 查找 Counter 类中名为 "count" 的字段，并获得其 VarHandle
            COUNT = MethodHandles.lookup().findVarHandle(Counter.class, "count", int.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private final int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        ExecutorService executor = Executors.newFixedThreadPool(12);

        Callable<Void> task = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (int i = 0; i < 1_000; i++) {
                    counter.increment();
                }
                return null;
            }
        };

        Future<Void>[] futures = new Future[12];
        for (int i = 0; i < 12; i++) {
            futures[i] = executor.submit(task);
        }
        for (Future<Void> future : futures) {
            try {
                future.get(); // 等待任务完成
            } catch (Exception e) {
                // 处理异常
            }
        }

        System.out.println("counter = " + counter.getCount());

        executor.shutdownNow();
        // executor.shutdown();
        // while (!executor.awaitTermination(1, TimeUnit.MILLISECONDS)) {
        //     System.out.println("running");
        // }


        // Counter counter = new Counter();
        // counter.increment();
        // System.out.println(counter.getCount()); // 输出: 1
    }

    public void increment() {
        // 原子性地递增 count
        COUNT.getAndAdd(this, 1);
    }

    public int getCount() {
        return (int) COUNT.get(this);
    }
}