package org.feng.local;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 03/30/2023 18:05
 * @Description:
 */
public class TestStart {

    @Test
    public void test() {
        ArrayList<String> list = Lists.newArrayList("feng", "you", "liang");
        List<String> collect = list.stream().filter(s -> !StringUtils.equals(s, "feng")).collect(Collectors.toList());

        System.out.println("System.identityHashCode(list) = " + System.identityHashCode(list));
        System.out.println("System.identityHashCode(collect) = " + System.identityHashCode(collect));

        System.out.println("list.hashCode() = " + list.hashCode());
        System.out.println("collect.hashCode() = " + collect.hashCode());
        System.out.println("list = " + list);
        System.out.println("collect = " + collect);
    }

    @Test
    public void test1() {
        long t = 0x1L << 63;
        System.out.println(t);
    }

    public static Supplier<Integer> closure() {
        final int[] count = {0};
        return () -> {
            count[0]++;
            return count[0];
        };
    }

    @Test
    public void test2() {

        try {
            System.setProperty("javax.net.debug", "ssl,handshake");
            URL url = new URL("https://api.monday.com");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {

    }
}
