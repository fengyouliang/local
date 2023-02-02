package org.feng.local.apache.commons.pool2.example;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 04/07/2023 14:52
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        ReaderUtil readerUtil = new ReaderUtil(new GenericObjectPool<>(new StringBufferFactory()));
    }
}
