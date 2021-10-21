package io.github.phiysng.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableDubbo
@EnableAutoConfiguration
public class DubboProvider {

    public static void main(String[] args) {
        SpringApplication.run(DubboProvider.class,args);
    }
}