package com.bobochang.yygh.hosp.service;

import com.bobochang.yygh.model.hosp.Hospital;
import com.bobochang.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/2-14:15
 **/
public interface HospitalService {
    //上传医院接口
    void saveHospital(Map<String, Object> paramMap);

    Hospital showHospitalByHoscode(String hoscode);

    Page<Hospital> list(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Map<String, Object> getHospDetailById(String id);

    String getHospName(String hoscode);

    List<Hospital> findByHosname(String hosname);

    Map<String, Object> findHospDetailByHoscode(String hoscode);
}
