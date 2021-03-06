package com.gabrielspassos.poc.enumerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssemblyResultEnumTest {

    @Test
    public void shouldReturnAccepted() {
        AssemblyResultEnum result = AssemblyResultEnum.getResult(2L, 1L);

        assertEquals(AssemblyResultEnum.ACCEPTED, result);
    }

    @Test
    public void shouldReturnDeclined() {
        AssemblyResultEnum result = AssemblyResultEnum.getResult(1L, 2L);

        assertEquals(AssemblyResultEnum.DECLINED, result);
    }

    @Test
    public void shouldReturnTied() {
        AssemblyResultEnum result = AssemblyResultEnum.getResult(1L, 1L);

        assertEquals(AssemblyResultEnum.TIED, result);
    }

}