package com.github.pondhia.ctrzit.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Ctrzit Config
 */
@Data
@Component
@Validated
@ConfigurationProperties(prefix = "ctrzit")
class CtrzitConfig {
    /**
     * Ctrzit version
     */
    @NotBlank
    private String version;

    /**
     * Upload path
     */
    @NotBlank
    private String uploadPath;

    /**
     * Download path
     */
    @NotBlank
    @Pattern(regexp = "^/.*", message = "Download path must be started with '/'")
    private String downloadPath;
}
