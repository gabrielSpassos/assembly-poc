package com.gabrielspassos.poc.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum CustomerStatusEnum {

    ABLE_TO_VOTE("ABLE_TO_VOTE"),
    UNABLE_TO_VOTE("UNABLE_TO_VOTE");

    private String customerStatus;
    private static Map<String, CustomerStatusEnum> map;

    static {
        map = Arrays.stream(CustomerStatusEnum.values())
                .collect(Collectors.toMap(CustomerStatusEnum::getCustomerStatus, Function.identity()));
    }

    public static CustomerStatusEnum getCustomerStatus(String status) {
        return map.getOrDefault(status, UNABLE_TO_VOTE);
    }
}
