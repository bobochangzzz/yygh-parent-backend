package com.bobochang.yygh.cmn.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bobochang
 * @description
 * @created 2022/6/29-15:02
 **/
@Configuration
@MapperScan("com.bobochang.yygh.cmn.mapper")
public class CmnConfig {

    /**
     * 配置分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
