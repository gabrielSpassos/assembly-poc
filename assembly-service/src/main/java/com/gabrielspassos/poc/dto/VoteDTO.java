package com.gabrielspassos.poc.dto;

import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteDTO {

    private VoteChoiceEnum voteChoice;
    private CustomerDTO customer;

}
