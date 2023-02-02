package org.feng.local.test;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class Main {

    public static void test() {
        Date expiresAt = DateUtils.addMinutes(new Date(), 30);
        long truncatedTimestamp = expiresAt.getTime() / 1000;
        System.out.println("expiresAt.getTime() = " + expiresAt.getTime());
        System.out.println("truncatedTimestamp = " + truncatedTimestamp);
    }

    public static void main(String[] args) {
        test();
    }
}
