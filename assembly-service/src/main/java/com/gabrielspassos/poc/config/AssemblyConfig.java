package com.gabrielspassos.poc.config;

import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class AssemblyConfig {

    @Value("${assembly.default-expiration-minutes}")
    private Long assemblyDefaultExpirationMinutes;

    private List<AssemblyStatusEnum> statusAbleToUpdate = List.of(AssemblyStatusEnum.OPEN, AssemblyStatusEnum.CLOSED);

}
