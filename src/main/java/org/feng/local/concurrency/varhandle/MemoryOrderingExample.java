package org.feng.local.concurrency.varhandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class MemoryOrderingExample {
    private static final VarHandle VALUE_HANDLE;

    static {
        try {
            VALUE_HANDLE = MethodHandles.lookup().findVarHandle(MemoryOrderingExample.class, "value", int.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private volatile int value;

    public static void main(String[] args) {
        MemoryOrderingExample example = new MemoryOrderingExample();

        example.orderedWrite(42);
        System.out.println("Ordered write value: " + example.getValue());

        example.delayedWrite(84);
        System.out.println("Delayed write value: " + example.getValue());
    }

    public void orderedWrite(int newValue) {
        // 有序写入：确保写入操作按照程序顺序执行，但不会立即对其他线程可见
        VALUE_HANDLE.setRelease(this, newValue);
    }

    public void delayedWrite(int newValue) {
        // 延迟写入：这种写入可能会被延迟，最终会对其他线程可见，但不会立即刷新
        VALUE_HANDLE.setOpaque(this, newValue);
    }

    public int getValue() {
        return (int) VALUE_HANDLE.getAcquire(this);
    }
}