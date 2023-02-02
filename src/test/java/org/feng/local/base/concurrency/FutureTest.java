package org.feng.local.base.concurrency;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 03/20/2023 15:40
 * @Description:
 */
@Slf4j
public class FutureTest {

    @Test
    public void test() {
        Map<String, List<String>> result = Maps.newHashMap();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture<List<String>> resultCompletableFuture = CompletableFuture.supplyAsync(this::getUserIds, executorService);

            resultCompletableFuture.thenAccept(new Consumer<List<String>>() {
                @Override
                public void accept(List<String> userIds) {
                    // int i = 1 / 0;
                    result.put("team-0", Optional.ofNullable(userIds).orElse(Lists.newArrayList()));
                }
            });

            resultCompletableFuture.exceptionally(new Function<Throwable, List<String>>() {
                @Override
                public List<String> apply(Throwable throwable) {
                    System.out.println("throwable = " + throwable);
                    return null;
                }
            });

            CompletableFuture<Map<String, List<String>>> mapCompletableFuture = CompletableFuture.supplyAsync(this::getMap, executorService);

            mapCompletableFuture.thenAccept(new Consumer<Map<String, List<String>>>() {
                @Override
                public void accept(Map<String, List<String>> stringListMap) {
                    result.putAll(Optional.ofNullable(stringListMap).orElse(Maps.newHashMap()));
                }
            });

            mapCompletableFuture.exceptionally(new Function<Throwable, Map<String, List<String>>>() {
                @Override
                public Map<String, List<String>> apply(Throwable throwable) {
                    System.out.println("throwable = " + throwable);
                    return null;
                }
            });

            resultCompletableFuture.get();

        } catch (Throwable throwable) {
            System.out.println("throwable = " + throwable);
        }

        System.out.println("result = " + result);
    }

    @Test
    public void test2() {
        Map<String, List<String>> result = Maps.newHashMap();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture<List<String>> listCompletableFuture = CompletableFuture.supplyAsync(this::getUserIds, executorService).whenComplete(new BiConsumer<List<String>, Throwable>() {
                @Override
                public void accept(List<String> strings, Throwable throwable) {
                    result.put("team-0", Optional.ofNullable(strings).orElse(Lists.newArrayList()));
                    if (Objects.nonNull(throwable)) {
                        System.out.println("throwable = " + throwable);
                    }
                }
            });
            listCompletableFuture.get();

            CompletableFuture<Map<String, List<String>>> mapCompletableFuture = CompletableFuture.supplyAsync(this::getMap, executorService).whenComplete((stringListMap, throwable) -> {
                result.putAll(Optional.ofNullable(stringListMap).orElse(Maps.newHashMap()));
                if (Objects.nonNull(throwable)) {
                    System.out.println("throwable = " + throwable);
                }
            });
            mapCompletableFuture.get();

        } catch (Throwable throwable) {
            System.out.println("throwable = " + throwable);
        }

        System.out.println("result = " + result);
    }


    private List<String> getUserIds() {
        int i = 1 / 0;

        return Lists.newArrayList("0-1", "0-2", "0-3");
    }

    private Map<String, List<String>> getMap() {

        Map<String, List<String>> map = Maps.newHashMap();
        map.put("team1", Lists.newArrayList("1-1", "1-2"));
        map.put("team2", Lists.newArrayList("2-1", "2-2"));
        map.put("team3", Lists.newArrayList("3-1", "3-2"));

        return map;
    }

    @Test
    public void test3() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<FutureTestTime> result = Lists.newCopyOnWriteArrayList();

        Random random = new Random();
        int maxNum = 2000;

        long startRun = System.currentTimeMillis();
        try {
            CompletableFuture<FutureTestTime> future1 = CompletableFuture.supplyAsync(() -> {
                String threadName = Thread.currentThread().getName();
                long start = System.currentTimeMillis();
                int randomTime = random.nextInt(maxNum);
                try {
                    Thread.sleep(randomTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long es = System.currentTimeMillis() - start;
                log.error("{}, {}, {}", threadName, randomTime, es);

                return new FutureTestTime(threadName, randomTime, es);
            }, threadPool).whenComplete((futureTestTime, throwable) -> {
                result.add(futureTestTime);
                if (Objects.nonNull(throwable)) {
                    log.error("{}", throwable);
                }
            });
            CompletableFuture<FutureTestTime> future2 = CompletableFuture.supplyAsync(() -> {
                String threadName = Thread.currentThread().getName();
                long start = System.currentTimeMillis();
                int randomTime = random.nextInt(maxNum);
                try {
                    Thread.sleep(randomTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long es = System.currentTimeMillis() - start;
                log.error("{}, {}, {}", threadName, randomTime, es);

                return new FutureTestTime(threadName, randomTime, es);
            }, threadPool).whenComplete((futureTestTime, throwable) -> {
                result.add(futureTestTime);
                if (Objects.nonNull(throwable)) {
                    log.error("{}", throwable);
                }
            });
            CompletableFuture<FutureTestTime> future3 = CompletableFuture.supplyAsync(() -> {
                String threadName = Thread.currentThread().getName();
                long start = System.currentTimeMillis();
                int randomTime = random.nextInt(maxNum);
                try {
                    Thread.sleep(randomTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long es = System.currentTimeMillis() - start;
                log.error("{}, {}, {}", threadName, randomTime, es);

                return new FutureTestTime(threadName, randomTime, es);
            }, threadPool).whenComplete((futureTestTime, throwable) -> {
                result.add(futureTestTime);
                if (Objects.nonNull(throwable)) {
                    log.error("{}", throwable);
                }
            });
            CompletableFuture<FutureTestTime> future4 = CompletableFuture.supplyAsync(() -> {
                String threadName = Thread.currentThread().getName();
                long start = System.currentTimeMillis();
                int randomTime = random.nextInt(maxNum);
                try {
                    Thread.sleep(randomTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long es = System.currentTimeMillis() - start;
                log.error("{}, {}, {}", threadName, randomTime, es);

                return new FutureTestTime(threadName, randomTime, es);
            }, threadPool).whenComplete((futureTestTime, throwable) -> {
                result.add(futureTestTime);
                if (Objects.nonNull(throwable)) {
                    log.error("{}", throwable);
                }
            });
            CompletableFuture<FutureTestTime> future5 = CompletableFuture.supplyAsync(() -> {
                String threadName = Thread.currentThread().getName();
                long start = System.currentTimeMillis();
                int randomTime = random.nextInt(maxNum);
                try {
                    Thread.sleep(randomTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long es = System.currentTimeMillis() - start;
                log.error("{}, {}, {}", threadName, randomTime, es);

                return new FutureTestTime(threadName, randomTime, es);
            }, threadPool).whenComplete((futureTestTime, throwable) -> {
                result.add(futureTestTime);
                if (Objects.nonNull(throwable)) {
                    log.error("{}", throwable);
                }
            });


            CompletableFuture.allOf(future1, future2, future3, future4, future5).get();

        } catch (Throwable throwable) {
            log.error("{}", throwable);
        }

        long endRun = System.currentTimeMillis();
        System.out.println("endRun - startRun = " + (endRun - startRun));
        System.out.println("result = " + result);

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FutureTestTime {
        String key;
        long random;
        long time;
    }

}

