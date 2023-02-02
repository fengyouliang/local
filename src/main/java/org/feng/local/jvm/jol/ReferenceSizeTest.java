package org.feng.local.jvm.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.HashMap;

public class ReferenceSizeTest {
    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
        System.out.println(ClassLayout.parseInstance(new HashMap<>()).toPrintable());
    }
}