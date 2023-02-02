package org.feng.local.base;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRulesProvider;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class DateTest {

    @Test
    public void testDate() {


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(1663832449000L));
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");

        // Here you say to java the initial timezone. This is the secret
        TimeZone timeZone = TimeZone.getTimeZone("GMT-8:00");
        sdf.setTimeZone(timeZone);
        System.out.println(timeZone.getDisplayName());
        // Will print in UTC
        System.out.println(sdf.format(calendar.getTime()));

        // Here you set to your timezone
        sdf.setTimeZone(TimeZone.getDefault());
        // Will print on your default Timezone
        System.out.println(sdf.format(calendar.getTime()));

        Set<String> zids = ZoneId.getAvailableZoneIds();
        System.out.println(zids);

        System.out.println();
    }

    @Test
    public void testDate1() {

        StringBuilder time = new StringBuilder();
        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        sdf.setTimeZone(timeZone);
        // String time = sdf.format(meeting.getStartTime());
        time.append(sdf.format(new Date(1663832449000L)));
        // String displayName = timeZone.getDisplayName();

        time.append(StringUtils.SPACE).append(timeZone.getDisplayName());

        System.out.println(time);
    }

    @Test
    public void test() {
        Long[] test = {1L, 0L, -1L};
        for (Long aLong : test) {
            System.out.println("StringUtils.equals(\"1\", String.valueOf(aLong)) = " + StringUtils.equals("1", String.valueOf(aLong)));
            System.out.println("StringUtils.equals(\"0\", String.valueOf(aLong)) = " + StringUtils.equals("0", String.valueOf(aLong)));
            System.out.println("StringUtils.equals(\"-1\", String.valueOf(aLong)) = " + StringUtils.equals("-1", String.valueOf(aLong)));
            System.out.println("String.valueOf(aLong) = " + String.valueOf(aLong));
        }
    }

    @Test
    public void test1() {
        int seconds = 501;

        String timeInHHMMSS = DurationFormatUtils.formatDuration(seconds, "HH:mm:ss.SSS", true);
        System.out.println("timeInHHMMSS = " + timeInHHMMSS);

        String format = String.format("%02d:%02d:%02d.000", seconds / 3600, (seconds / 60) % 60, seconds % 60);
        System.out.println("format = " + format);

    }

    @Test
    public void test2() {
        List<String> list = Lists.newArrayList();
        String s = list.get(0);
        System.out.println("s = " + s);

    }

    @Test
    public void test3() {
        Boolean bool = Boolean.TRUE;
        boolean equals = Objects.equals(bool, Boolean.TRUE);
        if (bool == Boolean.TRUE) {
            System.out.println("TRUE");
        } else if (bool == Boolean.FALSE) {
            System.out.println("FALSE");
        } else {
            System.out.println("else");
        }
    }

    @Test
    public void test4() {
        Map<String, List<String>> teamId2UserIdsMap = Maps.newHashMap();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                teamId2UserIdsMap.computeIfAbsent(String.valueOf(i), k -> new ArrayList<>()).add(String.valueOf(j));
            }
        }
        System.out.println("teamId2UserIdsMap = " + teamId2UserIdsMap);
    }

    @Test
    public void test5() {
        for (int i = 0; i < 10; i++) {
            String index = String.format("%03d", i);
            String crmId = "crmId:" + index;
            String userId = "userId:" + index;
            String email = "email.test" + index + "@zooom.us";

            String substring = email.substring(10, 13);
            System.out.println("substring = " + substring);
            int i1 = Integer.parseInt(substring);
            System.out.println("i1 = " + i1);
        }
    }

    @Test
    public void test6() {
        List<String> userIds = Stream.iterate(0, i -> i + 1).limit(10).map(String::valueOf).toList();
        log.info("userIds: {}", userIds);
    }

    @Test
    public void test7() {
        List<List<String>> subSets = new ArrayList<>(List.of(new ArrayList<>()));
        for (List<String> subSet : subSets) {
            System.out.println("size");
        }
        System.out.println(subSets);
    }

    @Test
    public void test8() {
        // ISO-8601 format string
        List<String> strings = List.of(
                "2023-11-27T19:00:00-08:00",
                "2023-11-27T18:00:00-09:00",
                "2023-11-27T19:00:00+08:00",
                "2023-11-27T20:00:00+09:00",

                "2018-02-18T02:30:00-07:00",
                "2018-02-18T08:00:00Z",
                "2023-11-27T18:59:40.937-08:00"
        );

        Set<String> allZoneIds = ZoneRulesProvider.getAvailableZoneIds();

        strings.forEach(iso8601String -> {
            // Parse the ISO-8601 string to an OffsetDateTime object
            OffsetDateTime odt = OffsetDateTime.parse(iso8601String, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            // Convert the OffsetDateTime object to an Instant object
            Instant instant = odt.toInstant();

            // Convert the Instant object to a timestamp (milliseconds since epoch)
            long timestamp = instant.toEpochMilli();

            // Print the timestamp
            System.out.println("Timestamp: " + timestamp);

            for (String zoneId : allZoneIds) {
                ZonedDateTime zdt = odt.atZoneSameInstant(ZoneId.of(zoneId));
                if (zdt.getOffset().equals(odt.getOffset())) {
                    System.out.println("Possible timezone for " + iso8601String + ": " + zoneId);
                }
            }
        });

    }

    @Test
    public void test9() {
        long l1 = 1719476770000L;
        long l2 = 1715476770000L;

        long l = l1 - l2;
        int i = (int) l;
        System.out.println("i = " + i);

    }

}
