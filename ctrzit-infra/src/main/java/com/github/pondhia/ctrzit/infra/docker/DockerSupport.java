package com.github.pondhia.ctrzit.infra.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.pondhia.ctrzit.common.config.DockerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Docker Support
 *
 * @author pondhia
 * @since 2023/12/20 20:48
 */
@Slf4j
@Component
public class DockerSupport {
    @Resource
    private DockerConfig dockerConfig;

    private DockerClient createClient() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerConfig.getHost())
                .build();
        ApacheDockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        return DockerClientImpl.getInstance(config, httpClient);
    }

    public boolean healthCheck() {
        try (DockerClient client = createClient()) {
            client.pingCmd().exec();
            return true;
        } catch (Exception e) {
            log.error("Docker Health Check Failed", e);
            return false;
        }
    }

    public boolean operateContainer(String container, Consumer<DockerClient> operation, String operationName) {
        try (DockerClient client = createClient()) {
            operation.accept(client);
            log.info("{} Successful for container: {}", operationName, container);
            return true;
        } catch (Exception e) {
            log.error("{} Failed for container: {}", operationName, container, e);
            return false;
        }
    }

    private List<ExposedPort> getExposedPorts(Collection<Integer> ports) {
        List<ExposedPort> list = new LinkedList<>();
        for (Integer port : ports) {
            ExposedPort exposedPort = ExposedPort.tcp(port);
            list.add(exposedPort);
        }
        return list;
    }

    private List<PortBinding> getPortBindings(Collection<ExposedPort> ports) {
        List<PortBinding> list = new LinkedList<>();
        for (ExposedPort port : ports) {
            Ports portBindings = new Ports();
            int bindPort = port.getPort();
            portBindings.bind(port, Ports.Binding.bindPort(bindPort));
        }

        return list;
    }

    public boolean createContainer(String name, String image, Collection<Integer> ports) {
        return operateContainer(null, client -> {
            List<ExposedPort> exposedPorts = getExposedPorts(ports);
            CreateContainerResponse response = client.createContainerCmd(image)
                    .withName(name)
                    .withExposedPorts(exposedPorts)
                    .withHostConfig(new HostConfig().withPortBindings(getPortBindings(exposedPorts)))
                    .exec();
        }, "Create Container");
    }

    public boolean startContainer(String container) {
        return operateContainer(container, client -> client.startContainerCmd(container).exec(), "Start Container");
    }

    public boolean stopContainer(String container) {
        return operateContainer(container, client -> client.stopContainerCmd(container).exec(), "Stop Container");
    }

    public boolean restartContainer(String container) {
        return operateContainer(container, client -> client.restartContainerCmd(container).exec(), "Restart Container");
    }

    public boolean deleteContainer(String container) {
        return operateContainer(container, client -> client.removeContainerCmd(container).exec(), "Delete Container");
    }
}
