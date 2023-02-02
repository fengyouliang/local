package org.feng.local.base;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.feng.local.test.Model;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@Slf4j
public class StreamTest {

    public static final int count = 0;

    @Test
    public void testPeek() {
        List<Model> modelList = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            modelList.add(Model.builder().build());
        }
        // List<Integer> list = IntStream.rangeClosed(0, 10)
        //         .boxed()
        //         .collect(Collectors.toList());
        List<Model> playlistDtoList = modelList.stream()
                .peek(new Consumer<Model>() {
                    @Override
                    public void accept(Model model) {
                        model.setTeamId("team");

                    }
                })
                .collect(Collectors.toList());

        modelList.forEach(new Consumer<Model>() {
            @Override
            public void accept(Model model) {
                System.out.println(model);
            }
        });
        playlistDtoList.forEach(new Consumer<Model>() {
            @Override
            public void accept(Model model) {
                System.out.println(model);
            }
        });
    }

    @Test
    public void testStream() {
        List<Model> modelList = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            modelList.add(Model.builder().build());
        }
        modelList.stream().filter(p -> p.equals("xxx")).findFirst().get();
    }
}