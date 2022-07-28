package com.bobochang.yygh.order.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.common.utils.AuthContextHolder;
import com.bobochang.yygh.enums.OrderStatusEnum;
import com.bobochang.yygh.model.order.OrderInfo;
import com.bobochang.yygh.order.service.OrderInfoService;
import com.bobochang.yygh.vo.order.OrderCountQueryVo;
import com.bobochang.yygh.vo.order.OrderQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/16-13:07
 **/

@Api(tags = "订单接口")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderApiController {

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation(value = "根据排班编号和就诊人编号生成挂号订单")
    @PostMapping("/auth/submitOrder/{scheduleId}/{patientId}")
    public Result saveOrders(@PathVariable String scheduleId,
                             @PathVariable Long patientId) {
        return Result.ok(orderInfoService.saveOrder(scheduleId, patientId));
    }


    @ApiOperation(value = "订单列表(条件查询带分页)")
    @GetMapping("auth/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       OrderQueryVo orderQueryVo, HttpServletRequest request) {
        //设置当前用户id
        orderQueryVo.setUserId(AuthContextHolder.getUserId(request));
        IPage<OrderInfo> pageParam = new Page<>(page,limit);
        IPage<OrderInfo> pageModel =
                orderInfoService.selectPage(pageParam,orderQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取订单状态")
    @GetMapping("auth/getStatusList")
    public Result getStatusList() {
        return Result.ok(OrderStatusEnum.getStatusList());
    }


    @ApiOperation(value = "根据订单id查询订单详情")
    @GetMapping("auth/getOrders/{orderId}")
    public Result getOrders(@PathVariable String orderId) {
        OrderInfo orderInfo = orderInfoService.getOrder(orderId);
        return Result.ok(orderInfo);
    }

    @ApiOperation(value = "取消预约")
    @GetMapping("auth/cancelOrder/{orderId}")
    public Result cancelOrder(
            @PathVariable("orderId") Long orderId) {
        return Result.ok(orderInfoService.cancelOrder(orderId));
    }

    @ApiOperation(value = "获取订单统计数据")
    @PostMapping("inner/getCountMap")
    public Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo) {
        return orderInfoService.getCountMap(orderCountQueryVo);
    }
}
