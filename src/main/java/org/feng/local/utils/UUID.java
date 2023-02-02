package org.feng.local.utils;


import org.apache.commons.codec.binary.Base64;

public class UUID {
    public UUID() {
    }

    public static void main(String[] args) {
        String random = random();
        System.out.println(random);
    }

    public static String random() {
        byte[] data = toByteArray(java.util.UUID.randomUUID());
        String s = Base64.encodeBase64URLSafeString(data);
        return s.split("=")[0];
    }

    public static String randomConfID() {
        byte[] data = toByteArray(java.util.UUID.randomUUID());
        return Base64.encodeBase64String(data);
    }

    public static byte[] toByteArray(java.util.UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        int i;
        for (i = 0; i < 8; ++i) {
            buffer[i] = (byte) ((int) (msb >>> 8 * (7 - i)));
        }

        for (i = 8; i < 16; ++i) {
            buffer[i] = (byte) ((int) (lsb >>> 8 * (7 - i)));
        }

        return buffer;
    }

    public static java.util.UUID toUUID(byte[] data) {
        long msb = 0L;
        long lsb = 0L;

        assert data.length == 16;

        int i;
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long) (data[i] & 255);
        }

        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long) (data[i] & 255);
        }

        java.util.UUID result = new java.util.UUID(msb, lsb);
        return result;
    }

    public static String toStandardUUID(String base64UUID) {
        byte[] data = Base64.decodeBase64(base64UUID);
        return toUUID(data).toString().toUpperCase();
    }

    public static String fromStandardUUID(String standardUUID) {
        byte[] data = toByteArray(java.util.UUID.fromString(standardUUID));
        return Base64.encodeBase64String(data);
    }
}