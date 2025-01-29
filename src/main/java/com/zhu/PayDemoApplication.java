package com.zhu;

import com.zhu.facepay.utils.Ip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayDemoApplication.class, args);
        log.info("公网地址："+ Ip.getPublicIpv4());
        log.info("内网地址："+ Ip.getLocalIpv4());
    }

}
