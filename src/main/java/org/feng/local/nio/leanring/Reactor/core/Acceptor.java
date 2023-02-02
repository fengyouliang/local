package org.feng.local.nio.leanring.Reactor.core;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 02/02/2023 11:32
 * @Description:
 */

public class Acceptor implements Runnable {

    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;

    Acceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try (SocketChannel socketChannel = serverSocketChannel.accept()) {
            if (socketChannel != null) {
                System.out.printf("收到来自 %s 的连接%n", socketChannel.getRemoteAddress());
                new AcceptorHandler(socketChannel, selector); // 这里把客户端通道传给Handler，Handler负责接下来的事件处理（除了连接事件以外的事件均可）
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
