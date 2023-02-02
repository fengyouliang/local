package org.feng.local.nio.leanring.Reactor.startup;

import org.feng.local.nio.leanring.Reactor.client.NIOClient;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 02/02/2023 11:42
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        new Thread(new NIOClient("127.0.0.1", 2333)).start();
        // new Thread(new NIOClient("127.0.0.1", 2333)).start();
    }
}
