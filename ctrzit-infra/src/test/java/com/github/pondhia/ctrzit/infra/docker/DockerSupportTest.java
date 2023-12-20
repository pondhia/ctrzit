package com.github.pondhia.ctrzit.infra.docker;

import com.github.pondhia.ctrzit.xx.TestApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * DockerSupportTest
 *
 * @author pondhia
 * @since 2023/12/20 21:45
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DockerSupportTest {

    private static final String CONTAINER_NAME = "redis";

    @Autowired
    private DockerSupport dockerSupport;

    @Test
    @Order(1)
    void testHealthCheck() {
        assertTrue(dockerSupport.healthCheck());
    }

    @Test
    @Order(2)
    void testCreateContainer() {
        assertTrue(dockerSupport.createContainer("redis", "redis:latest", List.of(6379)));
    }

    @Test
    @Order(3)
    void testStartContainer() {
        assertTrue(dockerSupport.startContainer(CONTAINER_NAME));
    }

    @Test
    @Order(4)
    void testRestartContainer() {
        assertTrue(dockerSupport.restartContainer(CONTAINER_NAME));
    }

    @Test
    @Order(5)
    void testStopContainer() {
        assertTrue(dockerSupport.stopContainer(CONTAINER_NAME));
    }

    @Test
    @Order(6)
    void testDeleteContainer() {
        assertTrue(dockerSupport.deleteContainer(CONTAINER_NAME));
    }
}