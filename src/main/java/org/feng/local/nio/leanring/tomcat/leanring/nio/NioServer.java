package org.feng.local.nio.leanring.tomcat.leanring.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: Youliang Feng
 * @Date: 02/07/2023 22:29
 * @Description: Tomcat架构解析NIO案例
 */
@Slf4j
public class NioServer {

    private Selector selector;

    public static void main(String[] args) throws IOException {
        NioServer nioServer = new NioServer();
        nioServer.init();
        nioServer.start();
    }

    public void init() throws IOException {
        this.selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8080);
        serverSocket.bind(inetSocketAddress);

        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        log.info("start {}", serverSocket);
    }

    public void start() throws IOException {
        while (true) {
            this.selector.select();
            Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                selectionKeyIterator.remove();

                if (selectionKey.isAcceptable()) {
                    accept(selectionKey);
                } else if (selectionKey.isReadable()) {
                    read(selectionKey);
                }
            }
        }
    }

    private void accept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel acceptSocketChannel = serverSocketChannel.accept();
        log.info("accept: {}", acceptSocketChannel);

        acceptSocketChannel.configureBlocking(false);

        acceptSocketChannel.register(this.selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);
        String request = new String(byteBuffer.array()).trim();
        log.info("request: {}", request);

        String outMessage = "request accept: " + request + "\n";
        ByteBuffer outBuffer = ByteBuffer.wrap(outMessage.getBytes());
        socketChannel.write(outBuffer);
    }
}
