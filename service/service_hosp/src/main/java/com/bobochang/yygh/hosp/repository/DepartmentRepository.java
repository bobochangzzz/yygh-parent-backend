package com.bobochang.yygh.hosp.repository;

import com.bobochang.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bobochang
 * @description
 * @created 2022/7/2-17:51
 **/

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
