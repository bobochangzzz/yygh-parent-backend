package com.bobochang.yygh.hosp.repository;

import com.bobochang.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author bobochang
 * @description
 * @created 2022/7/2-13:50
 **/

@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {
    //判断当前hospital对象是否存在hoscode
    Hospital getHospitalByHoscode(String hoscode);

    //根据医院名称模糊查询
    List<Hospital> findHospitalByHosnameLike(String hosname);
}
