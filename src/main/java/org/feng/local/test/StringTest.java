package org.feng.local.test;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Kevin Wen
 * @Date: 12/01/2023 14:47
 * @Description:
 */
@Slf4j
public class StringTest {


    // 定义包含数字和字母的字符集
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // 生成指定长度的随机字符串
    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        log.info("xxx", new Throwable("test"));
        // 生成60位的随机字符串
        String randomString = generateRandomString(59);
        randomString += "分";
        System.out.println("Generated Random String: " + randomString);
        byte[] bytes = randomString.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);
    }
}
