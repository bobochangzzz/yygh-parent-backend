package com.bobochang.yygh.hosp.controller.api;

import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.hosp.service.DepartmentService;
import com.bobochang.yygh.hosp.service.HospitalService;
import com.bobochang.yygh.hosp.service.HospitalSetService;
import com.bobochang.yygh.hosp.service.ScheduleService;
import com.bobochang.yygh.model.hosp.Hospital;
import com.bobochang.yygh.model.hosp.Schedule;
import com.bobochang.yygh.vo.hosp.DepartmentVo;
import com.bobochang.yygh.vo.hosp.HospitalQueryVo;
import com.bobochang.yygh.vo.hosp.ScheduleOrderVo;
import com.bobochang.yygh.vo.order.SignInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/4-16:52
 **/

@Api(tags = "医院管理Api接口")
@RestController
@RequestMapping("/api/hosp/hospital")
public class HospApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private HospitalSetService hospitalSetService;

    /**
     * 查询医院列表
     *
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @ApiOperation(value = "查询医院列表")
    @GetMapping("/findHospList/{page}/{limit}")
    public Result findHospList(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> hospitals = hospitalService.list(page, limit, hospitalQueryVo);
        return Result.ok(hospitals);
    }

    /**
     * 根据医院名称模糊查询
     *
     * @param hosname
     * @return
     */
    @ApiOperation(value = "根据医院名称查询")
    @GetMapping("/findByHosName/{hosname}")
    public Result findByHosName(@PathVariable String hosname) {
        List<Hospital> hospitalList = hospitalService.findByHosname(hosname);
        return Result.ok(hospitalList);
    }

    /**
     * 根据医院编号查询科室
     *
     * @param hoscode
     * @return
     */
    @ApiOperation(value = "根据医院编号查询科室")
    @GetMapping("/department/{hoscode}")
    public Result findDepartmentByHoscode(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }

    /**
     * 根据医院编号获取医院科室预约详情
     *
     * @param hoscode
     * @return
     */
    @ApiOperation(value = "根据医院编号获取医院科室预约详情")
    @GetMapping("/findHospDetail/{hoscode}")
    public Result findHospDetail(@PathVariable String hoscode) {
        Map<String, Object> result = hospitalService.findHospDetailByHoscode(hoscode);
        return Result.ok(result);
    }

    @ApiOperation(value = "根据医院编号和科室编号获取课预约排班数据")
    @GetMapping("/auth/getBookingScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getBookingSchedule(@PathVariable Integer page,
                                     @PathVariable Integer limit,
                                     @PathVariable String hoscode,
                                     @PathVariable String depcode) {
        return Result.ok(scheduleService.getBookingScheduleRule(page, limit, hoscode, depcode));
    }

    @ApiOperation(value = "根据医院编号/科室编号/排班日期获取排班数据")
    @GetMapping("/auth/findScheduleList/{hoscode}/{depcode}/{workDate}")
    public Result findScheduleList(@PathVariable String hoscode,
                                   @PathVariable String depcode,
                                   @PathVariable String workDate) {
        return Result.ok(scheduleService.getDetailSchedule(hoscode, depcode, workDate));
    }

    @ApiOperation(value = "根据排班id获取排班数据")
    @GetMapping("/getSchedule/{scheduleId}")
    public Result getSchedule(@PathVariable String scheduleId) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        return Result.ok(schedule);
    }

    @ApiOperation(value = "根据排班id获取预约下单数据")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(@PathVariable String scheduleId) {
        return scheduleService.getScheduleOrderVo(scheduleId);
    }

    @ApiOperation(value = "根据医院编号获取医院签名信息")
    @GetMapping("inner/getSignInfoVo/{hoscode}")
    public SignInfoVo getSignInfoVo(
            @PathVariable("hoscode") String hoscode) {
        return hospitalSetService.getSignInfoVo(hoscode);
    }
}
