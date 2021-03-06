package com.gabrielspassos.poc.entity;

import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "assembly")
public class AssemblyEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private AssemblyStatusEnum status;
    private LocalDateTime registerDateTime;
    private LocalDateTime updateDateTime;
    private LocalDateTime expirationDateTime;

}
