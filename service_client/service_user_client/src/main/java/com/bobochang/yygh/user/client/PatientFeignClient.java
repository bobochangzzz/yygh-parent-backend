package com.bobochang.yygh.user.client;

import com.bobochang.yygh.model.user.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author bobochang
 * @description
 * @created 2022/7/16-14:52
 **/
@FeignClient(value = "service-user")
@Repository
public interface PatientFeignClient {

    //根据就诊人id获取就诊人信息
    @GetMapping("/api/user/patient/inner/get/{id}")
    Patient getPatient(@PathVariable("id") Long id);

}
