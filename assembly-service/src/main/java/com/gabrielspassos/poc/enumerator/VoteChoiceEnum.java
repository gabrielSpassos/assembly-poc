package com.gabrielspassos.poc.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum VoteChoiceEnum {

    ACCEPTED("Sim"),
    DECLINED("Não");

    private String customerChoice;
    private static Map<String, VoteChoiceEnum> map;

    static {
        map = Arrays.stream(VoteChoiceEnum.values())
                .collect(Collectors.toMap(VoteChoiceEnum::getCustomerChoice, Function.identity()));
    }

    public static VoteChoiceEnum getVoteByCustomerChoice(String customerChoice) {
        return map.get(customerChoice);
    }
}
