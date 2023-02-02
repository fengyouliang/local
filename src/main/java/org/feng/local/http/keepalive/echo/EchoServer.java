package org.feng.local.http.keepalive.echo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Echo Server is running on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new EchoClientHandler(clientSocket)).start();
        }
    }
}

class EchoClientHandler implements Runnable {
    private final Socket clientSocket;

    public EchoClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            try {
                System.out.println(clientSocket);
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                // 读取客户端发送的HTTP请求头
                ByteArrayOutputStream requestHeaders = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    requestHeaders.write(buffer, 0, bytesRead);
                    System.out.println(requestHeaders);
                    // 检查是否读取完了请求头（双换行符分隔）
                    String headers = requestHeaders.toString(StandardCharsets.UTF_8);
                    if (headers.contains("\r\n\r\n")) {
                        break;
                    }
                }

                // 构造响应，将客户端发送的请求头返回
                String responseHeaders = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: " + requestHeaders.size() + "\r\n" +
                        "Connection: keep-alive\r\n" +
                        "\r\n";

                outputStream.write(responseHeaders.getBytes());
                outputStream.write(requestHeaders.toByteArray());
                outputStream.write("youliang".getBytes());
                outputStream.flush();
                System.out.println("write");

                // 关闭连接

            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;
            if (count > 10) {
                break;
            }
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
