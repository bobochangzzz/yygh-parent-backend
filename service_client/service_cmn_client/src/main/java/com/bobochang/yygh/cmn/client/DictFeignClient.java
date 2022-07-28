package com.bobochang.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author bobochang
 * @description
 * @created 2022/7/3-15:34
 **/
@FeignClient("service-cmn")
@Service
public interface DictFeignClient {

    @GetMapping(value = "/admin/cmn/dict/getName/{dictCode}/{value}")
    String getName(@PathVariable("dictCode") String dictCode,@PathVariable("value") String value);


    @GetMapping(value = "/admin/cmn/dict/getName/{value}")
    String getName(@PathVariable("value") String value);

}
