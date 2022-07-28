package com.bobochang.yygh.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bobochang
 * @description
 * @created 2022/7/16-12:58
 **/

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@ComponentScan(basePackages = {"com.bobochang"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.bobochang"})
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}
