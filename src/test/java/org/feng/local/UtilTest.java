package org.feng.local;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 01/10/2023 11:11
 * @Description:
 */
public class UtilTest {

    @Test
    public void testCollectionEqual() {
        List<String> list1 = Lists.newArrayList("feng", "you", "liang");
        List<String> list2 = Lists.newArrayList("feng", "liang", "you");

        boolean equalCollection = CollectionUtils.isEqualCollection(list1, list2);
        System.out.println("equalCollection = " + equalCollection);

        long currentTimeMillis = System.currentTimeMillis();
        long time = new Date(currentTimeMillis + TimeUnit.MINUTES.toMillis(30)).getTime();
        System.out.println("time = " + time);
    }
}
