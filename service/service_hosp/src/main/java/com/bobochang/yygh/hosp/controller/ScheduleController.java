package com.bobochang.yygh.hosp.controller;

import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.hosp.service.ScheduleService;
import com.bobochang.yygh.model.hosp.Schedule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/4-11:07
 **/
@Api(tags = "排班管理")
@RestController
@RequestMapping("/admin/hosp/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 根据医院编号和部门编号查看排班规则
     *
     * @param page
     * @param limit
     * @param hoscode
     * @param depcode
     * @return
     */
    @ApiOperation(value = "根据医院编号和部门编号查看排班规则")
    @GetMapping("/getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable Integer page,
                                  @PathVariable Integer limit,
                                  @PathVariable String hoscode,
                                  @PathVariable String depcode) {
        Map<String, Object> map = scheduleService.getBookingScheduleRule(page, limit, hoscode, depcode);
        return Result.ok(map);
    }

    @ApiOperation(value = "根据医院编号、科室编号和工作日期查询")
    @GetMapping("/getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    public Result getScheduleDetail(@PathVariable String hoscode,
                                    @PathVariable String depcode,
                                    @PathVariable String workDate) {
        List<Schedule> list = scheduleService.getDetailSchedule(hoscode, depcode, workDate);
        return Result.ok(list);
    }
}
