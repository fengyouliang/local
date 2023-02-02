package org.feng.local.base.string;

import cn.hutool.core.date.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class StringTest {

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

    private Set<String> splitToSet(String data) {
        Set<String> dataSet = new HashSet<>();
        if (StringUtils.isNotEmpty(data)) {
            String[] items = StringUtils.split(data, ",; ");
            for (String item : items) {
                item = item.trim();
                dataSet.add(item);
            }
        }
        return dataSet;
    }

    @Test
    public void testSplit() {
        System.out.println("splitToSet(\"feng,\\n you;\\nliang\") = " + splitToSet("feng;\n you;\nliang"));
    }

    @Test
    public void StringUtilsTest() {
        boolean containsIgnoreCase = StringUtils.containsIgnoreCase("Feng", "FENG");
        System.out.println("containsIgnoreCase = " + containsIgnoreCase);
    }

    @Test
    private void testStringAppend(Long count) {
        String string = "";
        for (int i = 0; i < count; i++) {
            string += i;
        }
    }

    @Test
    private void testStringBuilderAppend(Long count) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < count; i++) {
            string.append(i);
        }
    }

    @Test
    public void testStringAndStringBuilder() {
        Long count = 100000L;
        StopWatch stopWatch = new StopWatch("testStringAndStringBuilder");

        stopWatch.start("testStringAppend");
        testStringAppend(count);
        stopWatch.stop();

        stopWatch.start("testStringBuilderAppend");
        testStringBuilderAppend(count);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    @Test
    public void testBytes() throws UnsupportedEncodingException {
        String hello = new String("Hello冯");
        byte[] bytes = hello.getBytes(StandardCharsets.UTF_8);
        // byte[] bytes = {0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x51af};
        String s = new String(bytes);
        byte[] b1 = hello.getBytes(); // 按系统默认编码转换，不推荐
        byte[] b2 = hello.getBytes(StandardCharsets.UTF_8); // 按UTF-8编码转换
        byte[] b3 = hello.getBytes("GBK"); // 按GBK编码转换
        byte[] b4 = hello.getBytes(StandardCharsets.UTF_8); // 按UTF-8编码转换
        System.out.println("b1 = " + Arrays.toString(b1));
        System.out.println("b2 = " + Arrays.toString(b2));
        System.out.println("b3 = " + Arrays.toString(b3));
        System.out.println("b4 = " + Arrays.toString(b4));
    }


    @Test
    public void testBytesLength() {
        // 生成60位的随机字符串
        String randomString = generateRandomString(59);
        randomString += "分";
        System.out.println("Generated Random String: " + randomString);
        byte[] bytes = randomString.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);
    }

    @Test
    public void testString() {
        String str1 = "Hello";
        String str2 = new String("Hello");

        System.out.println(str1 == str2); // false (比较引用，不同)
        System.out.println(str1.equals(str2)); // true (比较内容，相同)
    }

}
