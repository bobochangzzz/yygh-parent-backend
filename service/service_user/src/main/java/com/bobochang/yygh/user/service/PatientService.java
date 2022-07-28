package com.bobochang.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bobochang.yygh.model.user.Patient;

import java.util.List;

/**
 * @author bobochang
 * @description
 * @created 2022/7/13-14:34
 **/
public interface PatientService extends IService<Patient> {
    List<Patient> findAllByUserId(Long userId);

    Patient getPatientById(Long id);
}
