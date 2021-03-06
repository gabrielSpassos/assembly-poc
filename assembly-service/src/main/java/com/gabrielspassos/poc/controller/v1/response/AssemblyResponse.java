package com.gabrielspassos.poc.controller.v1.response;

import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AssemblyResponse {

    private String id;
    private String name;
    private String description;
    private AssemblyStatusEnum status;
    private LocalDateTime registerDateTime;
    private LocalDateTime updateDateTime;
    private LocalDateTime expirationDateTime;
    private List<VoteResponse> votes;

}
