package org.feng.local.nio.leanring.Reactor.client;


import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 02/02/2023 11:37
 * @Description:
 */

public class Connector implements Runnable {

    private final Selector selector;

    private final SocketChannel socketChannel;

    Connector(SocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            if (socketChannel.finishConnect()) { // 这里连接完成（与服务端的三次握手完成）
                System.out.printf("已完成 %s 的连接%n", socketChannel.getRemoteAddress());
                new ConnectorHandler(socketChannel, selector); // 连接建立完成后，接下来的动作交给Handler去处理（读写等）
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
