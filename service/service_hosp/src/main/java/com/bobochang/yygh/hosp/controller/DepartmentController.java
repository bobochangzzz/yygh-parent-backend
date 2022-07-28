package com.bobochang.yygh.hosp.controller;

import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.hosp.service.DepartmentService;
import com.bobochang.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author bobochang
 * @description
 * @created 2022/7/3-20:49
 **/

@Api(tags = "医院科室管理")
@RestController
@RequestMapping("/admin/hosp/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 根据医院编号查询该医院下所有的科室
     * @param hoscode
     * @return
     */
    @ApiOperation(value = "查询医院所有科室")
    @GetMapping("getDepartLIst/{hoscode}")
    public Result getDepartList(@PathVariable String hoscode){
        List<DepartmentVo> departmentVoList = departmentService.findDeptTree(hoscode);
        return Result.ok(departmentVoList);
    }
}
