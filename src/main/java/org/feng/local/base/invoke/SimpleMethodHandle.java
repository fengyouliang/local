package org.feng.local.base.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class SimpleMethodHandle {
    public static void main(String[] args) throws Throwable {
        Object obj = (System.currentTimeMillis() & 1L) == 0L ? System.out : new MyPrintln();
        System.out.println(obj.getClass());
        getPrintlnMethodHandler(obj).invokeExact("geym");
    }

    private static MethodHandle getPrintlnMethodHandler(Object receiver) throws Throwable {
        MethodType mt = MethodType.methodType(void.class, String.class);

        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
    }

    static class MyPrintln {
        protected void println(String s) {
            System.out.println(s);
        }
    }
}
