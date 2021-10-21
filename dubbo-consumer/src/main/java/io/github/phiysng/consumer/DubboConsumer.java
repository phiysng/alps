package io.github.phiysng.consumer;

import io.github.phiysng.common.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class DubboConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345/")
    private HelloService helloService;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumer.class).close();
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            logger.error(helloService.sayHello("phiysng on github.com"));
        };
    }
}