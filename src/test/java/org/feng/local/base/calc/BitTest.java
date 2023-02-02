package org.feng.local.base.calc;

import org.junit.jupiter.api.Test;

public class BitTest {

    public static final long L1 = 0x1L;
    public static final long L2 = 0x1L << 1;
    public static final long L3 = 0x1L;


    @Test
    public void testBit() {
        testBit(true, true, true);
        testBit(false, true, true);
        testBit(true, true, false);
        testBit(false, false, true);
        testBit(true, false, true);
    }

    private void testBit(boolean flag1, boolean flag2, boolean flag3) {
        long teamOptions = (flag1 ? L2 : 0) | (flag2 ? L1 : 0);
        long nodeOptions = flag3 ? L3 : 0;

        System.out.println("teamOptions = " + Long.toString(teamOptions, 2));
        System.out.println("nodeOptions = " + Long.toString(nodeOptions, 2));
    }
}
