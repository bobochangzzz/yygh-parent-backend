package com.bobochang.yygh.hosp.service;

import com.bobochang.yygh.model.hosp.Department;
import com.bobochang.yygh.vo.hosp.DepartmentQueryVo;
import com.bobochang.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/2-17:52
 **/
public interface DepartmentService {
    void saveDepartment(Map<String, Object> paramMap);

    Page<Department> findDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);

    List<DepartmentVo> findDeptTree(String hoscode);

    String getDepName(String hoscode, String depcode);

    Department getDepartment(String hoscode, String depcode);
}
