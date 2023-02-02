package org.feng.local.base.nbac;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        Field[] fields = FieldOut.FieldIn.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType() + " " + field.getName() + " : " + field.isSynthetic());
        }

        // javac Main.java FieldOut.java

    }
}
