package com.foodorder.controller;

import com.foodorder.common.Result;
import com.foodorder.dto.LoginRequest;
import com.foodorder.dto.LoginResponse;
import com.foodorder.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * POST /api/auth/login
     * Body: { "username": "admin", "password": "123456" }
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Validated @RequestBody LoginRequest req) {
        return Result.success(authService.login(req));
    }
}
