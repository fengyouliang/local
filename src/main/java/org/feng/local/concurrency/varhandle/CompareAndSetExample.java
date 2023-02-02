package org.feng.local.concurrency.varhandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class CompareAndSetExample {
    private static final VarHandle VALUE_HANDLE;

    static {
        try {
            VALUE_HANDLE = MethodHandles.lookup().findVarHandle(CompareAndSetExample.class, "value", int.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private volatile int value;

    public static void main(String[] args) {
        CompareAndSetExample example = new CompareAndSetExample();

        // 初始值为 0
        System.out.println("Initial value: " + example.getValue());

        // 使用 CAS 操作将值从 0 更新为 1
        boolean success = example.compareAndSet(0, 1);
        System.out.println("CompareAndSet result: " + success); // 输出: true
        System.out.println("Updated value: " + example.getValue()); // 输出: 1
    }

    public boolean compareAndSet(int expected, int newValue) {
        return VALUE_HANDLE.compareAndSet(this, expected, newValue);
    }

    public int getValue() {
        return (int) VALUE_HANDLE.get(this);
    }
}