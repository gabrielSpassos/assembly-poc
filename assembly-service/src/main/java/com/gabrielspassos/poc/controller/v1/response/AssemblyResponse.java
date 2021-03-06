package com.gabrielspassos.poc.controller.v1.response;

import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class AssemblyResponse {

    private String id;
    private String name;
    private String description;
    private AssemblyStatusEnum status;
    private String registerDateTime;
    private String updateDateTime;
    private String expirationDateTime;
    private List<VoteResponse> votes;

}
