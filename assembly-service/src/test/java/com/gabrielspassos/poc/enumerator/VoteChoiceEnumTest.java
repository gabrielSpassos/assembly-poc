package com.gabrielspassos.poc.enumerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class VoteChoiceEnumTest {

    @Test
    public void shouldReturnAccepted() {
        VoteChoiceEnum choice = VoteChoiceEnum.getVoteByCustomerChoice("Sim");

        assertEquals(VoteChoiceEnum.ACCEPTED, choice);
    }

    @Test
    public void shouldReturnDeclined() {
        VoteChoiceEnum choice = VoteChoiceEnum.getVoteByCustomerChoice("Não");

        assertEquals(VoteChoiceEnum.DECLINED, choice);
    }

    @Test
    public void shouldReturnNull() {
        VoteChoiceEnum choice = VoteChoiceEnum.getVoteByCustomerChoice("invalid_choice");

        assertNull(choice);
    }

}