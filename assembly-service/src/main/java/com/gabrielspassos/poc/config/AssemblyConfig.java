package com.gabrielspassos.poc.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AssemblyConfig {

    @Value("${assembly.default-expiration-minutes}")
    private Long assemblyDefaultExpirationMinutes;

}
