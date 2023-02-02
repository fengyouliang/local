package org.feng.local.test;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 05/23/2023 14:58
 * @Description:
 */
public class SneakyTest {
    public static void main(String[] args) {
        f();
    }

    public static void f() {
        String[] a = new String[2];
        Object[] b = a;
        a[0] = "hi";
        b[1] = Integer.valueOf(42);
    }
}
