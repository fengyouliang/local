package org.feng.local.base.invoke;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;

public class VarHandleAllMethodsExample {

    public int instanceVar = 10;
    public static volatile boolean staticFlag = false;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        VarHandleAllMethodsExample example = new VarHandleAllMethodsExample();

        // 1. 获取 VarHandle 实例
        VarHandle instanceVarHandle = MethodHandles.lookup().findVarHandle(
                VarHandleAllMethodsExample.class, "instanceVar", int.class);
        VarHandle staticFlagHandle = MethodHandles.lookup().findStaticVarHandle(
                VarHandleAllMethodsExample.class, "staticFlag", boolean.class);
        VarHandle atomicIntegerHandle = MethodHandles.lookup().findStaticVarHandle(
                VarHandleAllMethodsExample.class, "atomicInteger", AtomicInteger.class);

        // 2. 基础访问方法
        // 2.1 get() 和 set()
        int value = (int) instanceVarHandle.get(example); // 获取实例变量值
        instanceVarHandle.set(example, 20); // 设置实例变量值

        boolean flag = (boolean) staticFlagHandle.get(); // 获取静态变量值
        staticFlagHandle.set(true); // 设置静态变量值

        // 2.2 getVolatile() 和 setVolatile()
        boolean volatileFlag = (boolean) staticFlagHandle.getVolatile(); // 获取 volatile 变量值
        staticFlagHandle.setVolatile(false); // 设置 volatile 变量值


        // 3. 原子操作方法
        // 3.1 compareAndSet() / compareAndExchange()
        boolean success = instanceVarHandle.compareAndSet(example, 20, 30); // 比较并设置实例变量值
        int exchangedValue = (int) instanceVarHandle.compareAndExchange(example, 30, 40); // 比较并交换实例变量值

        // 3.2 weakCompareAndSet() / weakCompareAndExchange()
        success = instanceVarHandle.weakCompareAndSet(example, 40, 50); // 弱比较并设置实例变量值
        // exchangedValue = (int) instanceVarHandle.weakCompareAndExchange(example, 50, 60); // 弱比较并交换实例变量值

        // 3.3 getAndAdd() / addAndGet()
        int oldValue = (int) instanceVarHandle.getAndAdd(example, 5); // 获取并增加实例变量值
        // int newValue = (int) instanceVarHandle.addAndGet(example, 5); // 增加并获取实例变量值

        // 3.4 getAndSet()
        oldValue = (int) instanceVarHandle.getAndSet(example, 80); // 获取并设置实例变量值

        // 3.5 getAndBitwiseOr() / getAndBitwiseAnd() / getAndBitwiseXor()
        oldValue = (int) instanceVarHandle.getAndBitwiseOr(example, 0b100); // 获取并按位或实例变量值
        oldValue = (int) instanceVarHandle.getAndBitwiseAnd(example, 0b110); // 获取并按位与实例变量值
        oldValue = (int) instanceVarHandle.getAndBitwiseXor(example, 0b101); // 获取并按位异或实例变量值

        // 3.6 bitwiseOrAndGet() / bitwiseAndAndGet() / bitwiseXorAndGet()
        // newValue = (int) instanceVarHandle.bitwiseOrAndGet(example, 0b100); // 按位或并获取实例变量值
        // newValue = (int) instanceVarHandle.bitwiseAndAndGet(example, 0b110); // 按位与并获取实例变量值
        // newValue = (int) instanceVarHandle.bitwiseXorAndGet(example, 0b101); // 按位异或并获取实例变量值


        // 4. 数组访问方法
        // 4.1 get() 和 set()
        int[] array = new int[]{1, 2, 3};
        VarHandle arrayHandle = MethodHandles.arrayElementVarHandle(int[].class);
        value = (int) arrayHandle.get(array, 1); // 获取数组元素值
        arrayHandle.set(array, 1, 4); // 设置数组元素值

        // 4.2 getVolatile() 和 setVolatile()
        value = (int) arrayHandle.getVolatile(array, 1); // 获取 volatile 数组元素值
        arrayHandle.setVolatile(array, 1, 5); // 设置 volatile 数组元素值

        // 4.3 compareAndSet() / compareAndExchange() 等原子操作方法
        success = arrayHandle.compareAndSet(array, 1, 5, 6); // 比较并设置数组元素值
        exchangedValue = (int) arrayHandle.compareAndExchange(array, 1, 6, 7); // 比较并交换数组元素值
        // ... 其他原子操作方法类似

    }
}