package org.feng.local.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Sets;
import org.feng.local.test.Model;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StreamTest {

    ObjectMapper mapper = new ObjectMapper();

    Model model1 = Model.builder().teamId("1").teamName("1").userIds(Lists.newArrayList("11", "12", "13", "14", "15")).age(1).flag(true).build();
    Model model2 = Model.builder().teamId("2").teamName("2").userIds(Lists.newArrayList("21", "22", "23", "24", "25")).age(2).flag(true).build();
    Model model3 = Model.builder().teamId("3").teamName("3").userIds(Lists.newArrayList("31", "32", "33", "34", "35")).age(3).flag(true).build();
    Model model4 = Model.builder().teamId("4").teamName(null).userIds(Lists.newArrayList("11", "22", "23", "44", "45")).age(4).flag(true).build();
    List<Model> models = Lists.newArrayList(model1, model2, model3, model4);

    @Test
    public void test() {

        List<String> names = models.stream().map(Model::getTeamName).filter(Objects::nonNull).toList();
        System.out.println("names = " + names);
        List<String> validNames = Lists.newArrayList("1", "2", null);
        System.out.println("validNames = " + validNames);

        models.forEach(model -> System.out.println("validNames.contains(model.getTeamName()) = " + validNames.contains(model.getTeamName())));

    }

    @Test
    public void test_filter() {
        List<Model> filter = models.stream().filter(model -> Objects.equals(model.getTeamName(), "1")).toList();
        filter.forEach(System.out::println);

    }

    @Test
    public void test_map() throws JsonProcessingException {
        List<Model> modelList = Lists.newArrayList(Model.builder().teamId("1").userId("feng").build(), Model.builder().teamId("1").userId("you").build(), Model.builder().teamId("1").userId("liang").build(),

                Model.builder().teamId("2").userId("chen").build(), Model.builder().teamId("2").userId("shan").build());

        HashMap<String, List<String>> map = Maps.newHashMap();
        for (Model model : modelList) {
            // map.computeIfAbsent(model.getTeamId(), k -> new ArrayList<>()).add(model.getUserId());
            map.computeIfAbsent(model.getTeamId(), k -> Lists.newArrayList()).add(model.getUserId());
        }
        String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        System.out.println("map = " + jsonResult);

        System.out.println("map.remove(\"1\") = " + map.remove("1"));
        System.out.println("map.remove(\"3\") = " + map.remove("3"));
    }

    @Test
    public void test_findFirst() {
        Model model1 = Model.builder().teamId("1").teamName("1").userIds(Lists.newArrayList("11", "12", "13", "14", "15")).build();
        Model model2 = Model.builder().teamId("1").teamName("1").userIds(null).build();

        String firstUserId1 = Optional.ofNullable(model1.getUserIds()).orElseGet(Lists::newArrayList).stream().findFirst().map(String::toUpperCase).orElse("none");
        String firstUserId2 = Optional.ofNullable(model2.getUserIds()).orElseGet(Lists::newArrayList).stream().findFirst().map(String::toUpperCase).orElse("none");

        System.out.println("firstUserId1 = " + firstUserId1);
        System.out.println("firstUserId2 = " + firstUserId2);
    }

    @Test
    public void test_computeIfAbsent() throws JsonProcessingException {
        HashMap<String, List<String>> map = Maps.newHashMap();
        ArrayList<String> ids = Lists.newArrayList("11", "12", "13", "14", "15", "21", "22", "23", "24", "25");
        for (String id : ids) {
            map.computeIfAbsent(StringUtils.startsWith(id, "1") ? "feng" : "youliang", k -> new ArrayList<>()).add(id);

            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            System.out.println("map = " + jsonResult);
        }
        String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        System.out.println("map = " + jsonResult);
    }

    @Test
    public void test_distinct() {
        List<String> userIds = Lists.newArrayList("11", "12", "13", "14", "15", "11", "12");
        System.out.println("userIds = " + userIds);
        userIds = userIds.stream().distinct().toList();
        System.out.println("userIds = " + userIds);
    }

    @Test
    public void test_join() {
        List<String> userIds = Lists.newArrayList("11", "12", "13", "14", "15", "11", "12");
        String userIdJoin = String.join(",", userIds);
        System.out.println("userIdJoin = " + userIdJoin);
        HashSet<String> set = Sets.newHashSet(userIds);
        System.out.println("String.join(\",\", set) = " + String.join(",", set));

        System.out.println("String.join(\",\", Sets.newHashSet()) = " + String.join(",", Sets.newHashSet()));
        boolean flag = Objects.equals(String.join(",", Sets.newHashSet()), "");
        System.out.println("flag = " + flag);
    }

    @Test
    public void test_reduce() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = dateFormat.format(date);
        System.out.println("strDate = " + strDate);
    }

    @Test
    public void test_anyMatch() {
        boolean b = models.stream().anyMatch(model -> !model.isFlag());
        System.out.println("b = " + b);
    }


}
