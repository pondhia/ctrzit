package com.github.pondhia.ctrzit.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Docker Config
 */
@Data
@Component
@Validated
@ConfigurationProperties(prefix = "infra.docker")
public class DockerConfig {
    /**
     * Docker Host
     */
    @NotBlank
    private String host;
}
