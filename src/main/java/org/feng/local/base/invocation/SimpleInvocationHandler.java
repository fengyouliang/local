package org.feng.local.base.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SimpleInvocationHandler implements InvocationHandler {

    private final Object target;

    public SimpleInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在这一行设置断点
        System.out.println("Before method call");
        Object result = method.invoke(target, args);
        // 可以在这里再设置一个断点，查看方法调用后的行为
        System.out.println("After method call");
        return result;
    }
}