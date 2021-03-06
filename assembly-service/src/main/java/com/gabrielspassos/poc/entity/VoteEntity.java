package com.gabrielspassos.poc.entity;

import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "vote")
public class VoteEntity {

    @Id
    private String id;
    private String assemblyId;
    private VoteChoiceEnum voteChoice;
    private CustomerEntity customer;

}
