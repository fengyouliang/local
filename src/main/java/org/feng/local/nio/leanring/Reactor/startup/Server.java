package org.feng.local.nio.leanring.Reactor.startup;

import org.feng.local.nio.leanring.Reactor.core.Reactor;

import java.io.IOException;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 02/02/2023 11:41
 * @Description:
 */
public class Server {
    public static void main(String[] args) {
        try {
            new Thread(new Reactor(2333)).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
