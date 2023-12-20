package com.github.pondhia.ctrzit.xx;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * TestApplication
 *
 * @author pondhia
 * @since 2023/12/20 22:04
 */
@Configuration
@ComponentScan("com.github.pondhia.**")
@EntityScan("com.github.pondhia.**")
@SpringBootApplication
public class TestApplication {
}
