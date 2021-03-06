package com.gabrielspassos.poc.stub.entity;

import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.entity.VoteEntity;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;

public class AssemblyEntityStub {

    public static AssemblyEntity create() {
        LocalDateTime now = LocalDateTime.now();
        return create("id", "name", "desc", AssemblyStatusEnum.CLOSED,
                now, now, now, VoteEntityStub.create());
    }

    public static AssemblyEntity createNew() {
        LocalDateTime now = LocalDateTime.now();
        return create("id", "name", "desc", AssemblyStatusEnum.CLOSED,
                now, null, null);
    }

    public static AssemblyEntity createUpdate() {
        LocalDateTime now = LocalDateTime.now();
        return create("id", "name", "desc", AssemblyStatusEnum.OPEN,
                now, now, now);
    }

    public static AssemblyEntity createExpired(LocalDateTime expiration) {
        LocalDateTime now = LocalDateTime.now();
        return create("id", "name", "desc", AssemblyStatusEnum.EXPIRED,
                now, now, expiration);
    }

    public static AssemblyEntity createToExpire(LocalDateTime expiration) {
        LocalDateTime now = LocalDateTime.now();
        return create("id", "name", "desc", AssemblyStatusEnum.OPEN,
                now, now, expiration);
    }

    public static AssemblyEntity create(String id, String name, String description, AssemblyStatusEnum status,
                                        LocalDateTime registerDateTime, LocalDateTime updateDateTime,
                                        LocalDateTime expirationDateTime, VoteEntity... votes) {
        return AssemblyEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .status(status)
                .registerDateTime(registerDateTime)
                .updateDateTime(updateDateTime)
                .expirationDateTime(expirationDateTime)
                .votes(Lists.newArrayList(votes))
                .build();
    }
}
