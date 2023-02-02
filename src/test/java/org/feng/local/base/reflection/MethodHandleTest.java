package org.feng.local.base.reflection;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodHandleTest {


    @Test
    public void test1() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle CONCAT_MH = lookup.findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
        assertEquals("hello world", (String) CONCAT_MH.invokeExact("hello", " world"));
    }
}
