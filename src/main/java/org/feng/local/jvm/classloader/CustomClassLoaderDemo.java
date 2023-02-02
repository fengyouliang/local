package org.feng.local.jvm.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoaderDemo {

    public static void main(String[] args) throws Exception {
        // Define two custom class loaders
        ClassLoader classLoader1 = new CustomClassLoader("/Users/YouliangFeng/Code/java/local/target/classes/");
        ClassLoader classLoader2 = new CustomClassLoader("/Users/YouliangFeng/Code/java/local/target/classes/");

        // Load MyClass with the first class loader
        Class<?> class1 = classLoader1.loadClass("org.feng.local.jvm.classloader.MyClass");
        Object instance1 = class1.getDeclaredConstructor().newInstance();
        class1.getMethod("sayHello").invoke(instance1);  // This should work

        // Load MyClass with the second class loader
        Class<?> class2 = classLoader2.loadClass("org.feng.local.jvm.classloader.MyClass");
        Object instance2 = class2.getDeclaredConstructor().newInstance();
        class2.getMethod("sayHello").invoke(instance2);  // This should also work

        // Check if instances are of the same class
        boolean sameClass = class1 == class2;
        System.out.println("Class1 and Class2 are the same: " + sameClass);  // Should be false

        // Try casting instance1 to the type of instance1's class
        try {
            MyClass myClassInstance = (MyClass) instance1;  // This will fail
        } catch (ClassCastException e) {
            System.out.println("Cannot cast instance1 to instance1's class: " + e.getMessage());
        }

        // Try casting instance2 to the type of instance1's class
        try {
            MyClass myClassInstance = (MyClass) instance2;  // This will fail
        } catch (ClassCastException e) {
            System.out.println("Cannot cast instance2 to instance1's class: " + e.getMessage());
        }

        // MyClass myClassInstance = (MyClass) instance1;：如果instance1是由自定义的ClassLoader加载的MyClass的实例，而你尝试将其转换为由应用程序默认ClassLoader加载的MyClass类型，这个转换会失败。
        // MyClass myClassInstance = (MyClass) instance2;：同样的道理，instance2是用不同ClassLoader加载的MyClass的实例，因此转换为默认ClassLoader加载的MyClass也会失败。

        // Check class compatibility with instanceof
        boolean isInstanceOf = instance1 instanceof MyClass;  // Should be true if MyClass is in the same loader
        System.out.println("Instance1 is instance of MyClass: " + isInstanceOf);

        boolean isInstanceOfOther = instance2 instanceof MyClass;  // Should be false
        System.out.println("Instance2 is instance of MyClass: " + isInstanceOfOther);  // Should be false

        Thread.currentThread().join();
    }

    // // Custom ClassLoader for loading classes from a specific path
    // static class CustomClassLoader extends ClassLoader {
    //     private String path;
    //
    //     public CustomClassLoader(String path) {
    //         this.path = path;
    //     }
    //
    //     @Override
    //     protected Class<?> findClass(String name) throws ClassNotFoundException {
    //         try {
    //             String filePath = path + name + ".class";
    //             InputStream input = new FileInputStream(filePath);
    //             ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    //             int data = input.read();
    //
    //             while (data != -1) {
    //                 buffer.write(data);
    //                 data = input.read();
    //             }
    //
    //             input.close();
    //
    //             byte[] classData = buffer.toByteArray();
    //             return defineClass(name, classData, 0, classData.length);
    //         } catch (IOException e) {
    //             throw new ClassNotFoundException("Class not found", e);
    //         }
    //     }
    // }

    static class CustomClassLoader extends ClassLoader {
        private final String path;

        public CustomClassLoader(String path) {
            this.path = path;
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            // Only delegate to parent for java.* classes
            if (name.startsWith("java.")) {
                return super.loadClass(name);
            }

            // Otherwise, load the class ourselves
            return findClass(name);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                String filePath = path + name.replace('.', '/') + ".class";
                InputStream input = new FileInputStream(filePath);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int data = input.read();

                while (data != -1) {
                    buffer.write(data);
                    data = input.read();
                }

                input.close();

                byte[] classData = buffer.toByteArray();
                return defineClass(name, classData, 0, classData.length);
            } catch (IOException e) {
                throw new ClassNotFoundException("Class not found", e);
            }
        }
    }
}