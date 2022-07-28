package com.bobochang.yygh.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bobochang
 * @description
 * @created 2022/6/29-14:25
 **/
@SpringBootApplication()
@ComponentScan(basePackages = "com.bobochang")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.bobochang")
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class, args);
    }
}
