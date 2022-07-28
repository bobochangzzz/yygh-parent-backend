package com.bobochang.yygh.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.enums.OrderStatusEnum;
import com.bobochang.yygh.model.order.OrderInfo;
import com.bobochang.yygh.order.service.OrderInfoService;
import com.bobochang.yygh.vo.order.OrderQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bobochang
 * @description
 * @created 2022/7/18-13:09
 **/
@Api(tags = "订单接口")
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderController {

    @Autowired
    private OrderInfoService orderInfoServiceService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @PathVariable Long page,
            @PathVariable Long limit,
            OrderQueryVo orderQueryVo) {
        IPage<OrderInfo> pageParam = new Page<>(page, limit);
        IPage<OrderInfo> pageModel = orderInfoServiceService.selectPage(pageParam, orderQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取订单状态")
    @GetMapping("getStatusList")
    public Result getStatusList() {
        return Result.ok(OrderStatusEnum.getStatusList());
    }

    @ApiOperation(value = "获取订单")
    @GetMapping("show/{id}")
    public Result get(@PathVariable Long id) {
        return Result.ok(orderInfoServiceService.show(id));
    }

}
