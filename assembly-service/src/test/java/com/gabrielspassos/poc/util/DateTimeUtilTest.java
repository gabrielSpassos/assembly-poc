package com.gabrielspassos.poc.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    @Test
    public void shouldReturnDateTime() {
        LocalDateTime now = DateTimeUtil.now();

        assertNotNull(now);
    }

}