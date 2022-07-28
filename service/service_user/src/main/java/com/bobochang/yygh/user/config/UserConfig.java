package com.bobochang.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author bobochang
 * @description
 * @created 2022/7/5-22:40
 **/

@Configuration
@MapperScan("com.bobochang.yygh.user.mapper")
public class UserConfig {

}
