package com.bobochang.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bobochang.yygh.cmn.client.DictFeignClient;
import com.bobochang.yygh.hosp.repository.HospitalRepository;
import com.bobochang.yygh.hosp.service.HospitalService;
import com.bobochang.yygh.model.hosp.Hospital;
import com.bobochang.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/2-14:16
 **/
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void saveHospital(Map<String, Object> paramMap) {
        //将map转换为字符串进而转换为hospital对象
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);
        //判断当前hospital是否存在hoscode
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospitalExist != null) {
            hospital.setId(hospital.getId());
            hospital.setStatus(hospital.getStatus());
            hospital.setCreateTime(hospital.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
        } else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital showHospitalByHoscode(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospital;
    }

    @Override
    public Page<Hospital> list(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        //创建Pageable对象
        Pageable pageable = PageRequest.of(page - 1, limit);

        //将hospitalQueryVo对象转换为Hospital对象
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);

        //构建模糊查询
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Hospital> example = Example.of(hospital, matcher);
        Page<Hospital> pageInfo = hospitalRepository.findAll(example, pageable);

        //以流方式将Hospital对象中加入医院等级并进行封装
        pageInfo.getContent().stream().forEach(item -> {
            this.setHospitalHosType(item);
        });
        return pageInfo;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public Map<String, Object> getHospDetailById(String id) {
        Map<String, Object> result = new HashMap<>();
        Hospital hospital = this.setHospitalHosType(hospitalRepository.findById(id).get());
        result.put("hospital", hospital);
        result.put("bookingRule", hospital.getBookingRule());
        //避免重复返回
        hospital.setBookingRule(null);
        return result;
    }

    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        String hosname = hospital.getHosname();
        if (hosname != null) {
            return hosname;
        }
        return null;
    }

    @Override
    public List<Hospital> findByHosname(String hosname) {
        return hospitalRepository.findHospitalByHosnameLike(hosname);
    }

    @Override
    public Map<String, Object> findHospDetailByHoscode(String hoscode) {
        Map<String, Object> result = new HashMap<>();
        //医院详情
        Hospital hospital = this.setHospitalHosType(this.showHospitalByHoscode(hoscode));
        result.put("hospital", hospital);
        //预约规则
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return result;
    }

    private Hospital setHospitalHosType(Hospital hospital) {
        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("hostypeString", hostypeString);
        hospital.getParam().put("fullAddress", provinceString + cityString + districtString);
        return hospital;
    }
}
