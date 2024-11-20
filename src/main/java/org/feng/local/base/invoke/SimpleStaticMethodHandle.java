package org.feng.local.base.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * invokeSpecial
 *
 * @author Administrator
 */
public class SimpleStaticMethodHandle {
    public static void main(String[] args) throws Throwable {
        SimpleStaticMethodHandle obj = new SimpleStaticMethodHandle();
        obj.callSin();

        callStatic();
    }

    public void callSin() throws Throwable {
        MethodHandle mh = MethodHandles
                .lookup()
                .findStatic(Math.class, "sin", MethodType.methodType(double.class, double.class));
        double invoked = (double) mh.invokeExact(Math.PI / 2);
        System.out.println("invoked = " + invoked);
    }

    public static String generateDynamicString(MethodHandles.Lookup lookup, String name, MethodType type) {
        // 假设我们根据当前时间生成字符串
        return "Current time: " + System.currentTimeMillis();
    }

    public static void callStatic() throws Throwable {
        // 1. 创建 MethodHandle，指向 Bootstrap 方法
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType bootstrapType = MethodType.methodType(String.class, MethodHandles.Lookup.class, String.class, MethodType.class);
        MethodHandle bootstrapMethodHandle = lookup.findStatic(SimpleStaticMethodHandle.class, "generateDynamicString", bootstrapType);
        String dynamicString = (String) bootstrapMethodHandle.invoke(lookup, "name", bootstrapType);
        System.out.println(dynamicString);
    }
}