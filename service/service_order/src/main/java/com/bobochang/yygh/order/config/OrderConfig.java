package com.bobochang.yygh.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author bobochang
 * @description
 * @created 2022/7/16-13:09
 **/
@Configuration
@MapperScan("com.bobochang.yygh.order.mapper")
public class OrderConfig {
}
