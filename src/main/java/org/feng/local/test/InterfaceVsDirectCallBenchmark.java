package org.feng.local.test;

import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class InterfaceVsDirectCallBenchmark {

    private static final int ITERATIONS = 1000000000;
    private static final Interface instance = new Implementation();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(InterfaceVsDirectCallBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void interfaceCall() {
        for (int i = 0; i < ITERATIONS; i++) {
            instance.method();
        }
    }

    @Benchmark
    public void directCall() {
        Implementation impl = new Implementation();
        for (int i = 0; i < ITERATIONS; i++) {
            impl.method();
        }
    }

    private interface Interface {
        void method();
    }

    private static class Implementation implements Interface {
        @Override
        public void method() {
            // do something
        }
    }
}