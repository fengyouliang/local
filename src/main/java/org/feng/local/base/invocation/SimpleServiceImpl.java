package org.feng.local.base.invocation;

public class SimpleServiceImpl implements SimpleService {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}