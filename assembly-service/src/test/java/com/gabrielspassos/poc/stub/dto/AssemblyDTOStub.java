package com.gabrielspassos.poc.stub.dto;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;

import java.time.LocalDateTime;

public class AssemblyDTOStub {

    public static AssemblyDTO create() {
        LocalDateTime now = LocalDateTime.now();
        return create("id", "name", "desc", AssemblyStatusEnum.CLOSED,
                now, now, now);
    }

    public static AssemblyDTO createNew() {
        LocalDateTime now = LocalDateTime.parse("2021-03-05T23:38:32");
        return create("id", "name", "desc", AssemblyStatusEnum.CLOSED,
                now, null, null);
    }

    public static AssemblyDTO createWithExpire(LocalDateTime expiration) {
        LocalDateTime now = LocalDateTime.now();
        return create("id", "name", "desc", AssemblyStatusEnum.OPEN,
                now, now, expiration);
    }

    public static AssemblyDTO create(String id, String name, String description, AssemblyStatusEnum status,
                                        LocalDateTime registerDateTime, LocalDateTime updateDateTime,
                                        LocalDateTime expirationDateTime) {
        return AssemblyDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .status(status)
                .registerDateTime(registerDateTime)
                .updateDateTime(updateDateTime)
                .expirationDateTime(expirationDateTime)
                .build();
    }
}
