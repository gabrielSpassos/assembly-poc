package com.gabrielspassos.poc.dto;

import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitVoteDTO {

    private VoteChoiceEnum choice;
    private CustomerDTO customer;

}
