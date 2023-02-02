package org.feng.local.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Rule {
    private final Integer id;
    private final Long cableType;

    public Rule(Integer id, Long cableType) {
        this.id = id;
        this.cableType = cableType;
    }

    public Integer getId() {
        return id;
    }

    public Long getCableType() {
        return cableType;
    }

    @Override
    public String toString() {
        return "Rule{id=" + id + ", cableType=" + cableType + '}';
    }
}

public class DuplicateKeyExample {
    public static void main(String[] args) {
        List<Rule> ruleList = Arrays.asList(
                new Rule(1, 100L),
                new Rule(2, 200L),
                new Rule(1, 300L)  // 重复的键 1
        );

        try {
            Map<Integer, Long> map = ruleList.stream().collect(Collectors.toMap(Rule::getId, Rule::getCableType));
        } catch (IllegalStateException e) {
            System.err.println("Caught exception: " + e);
        }
    }
}
