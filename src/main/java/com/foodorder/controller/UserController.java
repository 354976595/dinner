package com.foodorder.controller;

import com.foodorder.common.Result;
import com.foodorder.dto.RechargeRequest;
import com.foodorder.entity.User;
import com.foodorder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * GET /api/user/info
     * 获取当前用户信息（含余额）
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getUser(userId));
    }

    /**
     * POST /api/user/recharge
     * 充值余额
     * Body: { "amount": 100, "gift": 10, "remark": "100元套餐" }
     */
    @PostMapping("/recharge")
    public Result<User> recharge(
            @Validated @RequestBody RechargeRequest req,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.recharge(userId, req));
    }
}
