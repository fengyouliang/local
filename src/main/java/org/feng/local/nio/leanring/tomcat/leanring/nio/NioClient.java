package org.feng.local.nio.leanring.tomcat.leanring.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 02/07/2023 22:50
 * @Description:
 */
@Slf4j
public class NioClient {
    private Selector selector;

    private final BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        NioClient nioClient = new NioClient();
        nioClient.init();
        nioClient.start();
    }

    public void init() throws IOException {
        this.selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
    }

    public void start() throws IOException {
        while (true) {
            this.selector.select();
            Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                selectionKeyIterator.remove();

                if (selectionKey.isConnectable()) {
                    connect(selectionKey);
                } else if (selectionKey.isReadable()) {
                    read(selectionKey);
                }
            }
        }
    }

    private void connect(SelectionKey selectionKey) throws IOException {

        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        if (socketChannel.finishConnect()) {
            socketChannel.configureBlocking(false);
            socketChannel.register(this.selector, SelectionKey.OP_READ);
            String request = clientInput.readLine();
            socketChannel.write(ByteBuffer.wrap(request.getBytes()));
        } else {
            selectionKey.cancel();
        }

    }

    private void read(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);

        String response = new String(byteBuffer.array()).trim();
        log.info("response: {}", response);

        String nextRequest = clientInput.readLine();
        ByteBuffer outBuffer = ByteBuffer.wrap(nextRequest.getBytes());
        socketChannel.write(outBuffer);
    }
}
