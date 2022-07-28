package com.bobochang.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bobochang.yygh.hosp.repository.DepartmentRepository;
import com.bobochang.yygh.hosp.service.DepartmentService;
import com.bobochang.yygh.model.hosp.Department;
import com.bobochang.yygh.vo.hosp.DepartmentQueryVo;
import com.bobochang.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bobochang
 * @description
 * @created 2022/7/2-17:52
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void saveDepartment(Map<String, Object> paramMap) {
        //将paramMap转换为Department对象
        Department department = JSONObject.parseObject(JSON.toJSONString(paramMap), Department.class);
        Department departmentExist = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        if (departmentExist != null) {
            departmentExist.setHoscode(departmentExist.getHoscode());
            departmentExist.setDepcode(departmentExist.getDepcode());
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        } else {
            department.setHoscode(department.getHoscode());
            department.setDepcode(department.getDepcode());
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        //创建Pageable对象 设置页数和每页记录数
        Pageable pageable = PageRequest.of(page - 1, limit);
        //构建Department对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);
        //创建Example对象
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Department> example = Example.of(department, matcher);
        Page<Department> pageInfo = departmentRepository.findAll(example, pageable);
        return pageInfo;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department departmentExist = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (departmentExist != null) {
            departmentRepository.deleteById(departmentExist.getId());
        }
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {

        //创建List集合用于最后结果的封装
        ArrayList<DepartmentVo> result = new ArrayList<>();

        //构建查询条件
        Department department = new Department();
        department.setHoscode(hoscode);
        Example<Department> example = Example.of(department);
        //根据医院编号 查询出该医院下所有的科室信息
        List<Department> departmentList = departmentRepository.findAll(example);

        //根据流的遍历 将所有的科室信息按照大科室进行分组
        Map<String, List<Department>> listMap = departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));

        //遍历map集合 进而得到每个大科室下的小科室
        for (Map.Entry<String, List<Department>> entry : listMap.entrySet()) {
            //获得大科室编号
            String bigCode = entry.getKey();
            //获得大科室对应的全局数据
            List<Department> entryValue = entry.getValue();

            //封装大科室
            DepartmentVo departmentVo1 = new DepartmentVo();
            departmentVo1.setDepcode(bigCode);
            departmentVo1.setDepname(entryValue.get(0).getBigname());

            //封装小科室
            ArrayList<DepartmentVo> children = new ArrayList<>();
            for (Department departmentOfSmallDep : entryValue) {
                DepartmentVo departmentVo2 = new DepartmentVo();
                departmentVo2.setDepname(departmentOfSmallDep.getDepname());
                departmentVo2.setDepcode(departmentOfSmallDep.getDepcode());
                //每一个小科室封装到list集合中
                children.add(departmentVo2);
            }
            //小科室集合封装到大科室集合中
            departmentVo1.setChildren(children);
            //将结果放到大集合中
            result.add(departmentVo1);
        }
        return result;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {

        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            return department.getDepname();
        }
        return null;

    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
    }
}
