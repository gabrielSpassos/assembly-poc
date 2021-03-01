package com.gabrielspassos.poc.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtil {

    private static final ZoneId BR_ZONE_ID = ZoneId.of("America/Sao_Paulo");

    public static LocalDateTime now() {
        return LocalDateTime.now(BR_ZONE_ID);
    }
}
