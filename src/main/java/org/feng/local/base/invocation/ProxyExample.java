package org.feng.local.base.invocation;

import java.lang.reflect.Proxy;

public class ProxyExample {
    public static void main(String[] args) {
        SimpleService originalService = new SimpleServiceImpl();
        SimpleService proxyService = (SimpleService) Proxy.newProxyInstance(
                SimpleService.class.getClassLoader(),
                new Class[]{SimpleService.class},
                new SimpleInvocationHandler(originalService));

        // 调用代理对象的方法，此时应触发断点
        proxyService.print("Hello, world!");
    }
}