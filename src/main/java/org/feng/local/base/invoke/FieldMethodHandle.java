package org.feng.local.base.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class FieldMethodHandle {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findGetter(User.class, "id", int.class);
        User u = new User();
        u.id = 5;
        System.out.println(mh.invoke(u));
    }

    public static class User {
        public int id;
    }
}
