package org.feng.local.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ByteBuddyTest {

    class Foo { // Foo的原始定义
        String bar() {
            return "bar";
        }
    }

    public static class DelegeteFoo {
        public String hello(String name) {
            System.out.println("DelegeteFoo:" + name);
            return null;
        }
    }


    public static class Interceptor {
        @RuntimeType
        public Object intercept(
                @This Object obj, // 目标对象
                @AllArguments Object[] allArguments, // 注入目标方法的全部参数
                @SuperCall Callable<?> zuper, // 调用目标方法，必不可少哦
                @Origin Method method, // 目标方法
                @Super DelegeteFoo delegeteFoo // 目标对象
        ) throws Exception {
            System.out.println("obj=" + obj);
            System.out.println("delegeteFoo =" + delegeteFoo);
            // 从上面两行输出可以看出，obj和db是一个对象
            try {
                return zuper.call(); // 调用目标方法
            } finally {
            }
        }

    }


    @Test
    public void annotateDelegateTest() throws IllegalAccessException, InstantiationException {

        DynamicType.Unloaded<DelegeteFoo> dynamicType = new ByteBuddy()
                .subclass(DelegeteFoo.class)
                .name("org.feng.local.bytebuddy.ByteBuddyTest.Foo")
                .method(named("hello"))
                .intercept(MethodDelegation.to(new Interceptor()))
                .make();

        // 加载字节码
        DynamicType.Loaded<DelegeteFoo> type = dynamicType.load(getClass().getClassLoader());

        // 输出类字节码
        outputClazz(dynamicType.getBytes(), "org.feng.local.bytebuddy.ByteBuddyTest.Foo");


        // 加载类
        Class<?> clazz = type.getLoaded();

        // 反射调用
        try {
            String bar = (String) clazz.getMethod("hello", String.class).invoke(clazz.newInstance(), "bar - from 疯狂创客圈");
            System.out.println(bar);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }

    private static void outputClazz(byte[] bytes, String clazzName) {
        FileOutputStream out = null;
        try {
            String pathName = ByteBuddyTest.class.getResource("/").getPath() + clazzName + ".class";
            out = new FileOutputStream(new File(pathName));
            System.out.println("类输出路径：" + pathName);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
