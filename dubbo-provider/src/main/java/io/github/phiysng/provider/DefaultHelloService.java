package io.github.phiysng.provider;

import io.github.phiysng.common.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@DubboService(version = "${demo.service.version}")
public class DefaultHelloService implements HelloService {

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        log.warn("io.github.phiysng.provider.DefaultHelloService.sayHello {}",name);
        return String.format("[%s] : Hello, %s", serviceName, name);
    }


}