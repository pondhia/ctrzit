package com.github.pondhia.ctrzit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Ctrzit Application
 */
@Slf4j
@EnableAsync
@SpringBootApplication
public class CtrzitApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CtrzitApplication.class);
    }

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(CtrzitApplication.class, args);

        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        path = Objects.isNull(path) ? "" : path;

        log.info("\n" + "=".repeat(60) + "\n\t" +
                "Containerize It!\n\n\t" +
                "Local:\t\thttp://localhost:" + port + path + "/\n\t" +
                "External:\thttp://" + ip + ":" + port + path + "/\n\n\t" +
                "Wiki:\t\thttps://github.com/pondhia/ctrzit/wiki\n\t" +
                "Issues:\t\thttps://github.com/pondhia/ctrzit/issues\n" +
                "=".repeat(60));

    }
}
