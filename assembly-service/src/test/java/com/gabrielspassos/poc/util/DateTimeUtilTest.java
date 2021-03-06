package com.gabrielspassos.poc.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateTimeUtilTest {

    @Test
    public void shouldReturnDateTime() {
        LocalDateTime now = DateTimeUtil.now();

        assertNotNull(now);
    }

}