package org.feng.local.http.keepalive.echo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class KeepAliveClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(host, port)) {
            System.out.println(socket);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            while (true) {
                // 发送HTTP GET请求，并保持连接
                String request = "GET / HTTP/1.1\r\n" +
                        "Host: " + host + "\r\n" +
                        "Connection: keep-alive\r\n" +
                        "\r\n";
                outputStream.write(request.getBytes());
                outputStream.flush();

                System.out.println("write");

                // 读取并打印完整的响应数据
                ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    responseBuffer.write(buffer, 0, bytesRead);
                    System.out.println(responseBuffer);
                    // 假设响应较短，读取完毕后停止（简单方式）
                    if ("youliang".equals(responseBuffer.toString())) {
                        break;
                    }
                }

                System.out.println("Response from server:");
                System.out.println(responseBuffer.toString(StandardCharsets.UTF_8));

                // 检查连接状态
                if (socket.isClosed()) {
                    System.out.println("Connection closed by server.");
                    break;
                } else {
                    System.out.println("Connection kept alive.");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
