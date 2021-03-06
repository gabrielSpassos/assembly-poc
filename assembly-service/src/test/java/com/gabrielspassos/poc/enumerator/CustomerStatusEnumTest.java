package com.gabrielspassos.poc.enumerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerStatusEnumTest {

    @Test
    public void shouldReturnAbleToVote() {
        CustomerStatusEnum customerStatusEnum = CustomerStatusEnum.getCustomerStatus("ABLE_TO_VOTE");

        assertEquals(CustomerStatusEnum.ABLE_TO_VOTE, customerStatusEnum);
    }

    @Test
    public void shouldReturnUnableToVote() {
        CustomerStatusEnum customerStatusEnum = CustomerStatusEnum.getCustomerStatus("UNABLE_TO_VOTE");

        assertEquals(CustomerStatusEnum.UNABLE_TO_VOTE, customerStatusEnum);
    }

    @Test
    public void shouldReturnUnableToVoteWithDefaultValue() {
        CustomerStatusEnum customerStatusEnum = CustomerStatusEnum.getCustomerStatus("invalid_status");

        assertEquals(CustomerStatusEnum.UNABLE_TO_VOTE, customerStatusEnum);
    }

}