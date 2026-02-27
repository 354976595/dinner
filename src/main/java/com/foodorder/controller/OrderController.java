package com.foodorder.controller;

import com.foodorder.common.Result;
import com.foodorder.dto.CreateOrderRequest;
import com.foodorder.dto.OrderVO;
import com.foodorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * POST /api/order/create
     * 提交订单
     */
    @PostMapping("/create")
    public Result<OrderVO> createOrder(
            @Validated @RequestBody CreateOrderRequest req,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.createOrder(userId, req));
    }

    /**
     * GET /api/order/list
     * 查看当前用户历史订单
     */
    @GetMapping("/list")
    public Result<List<OrderVO>> listOrders(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.getUserOrders(userId));
    }
}
