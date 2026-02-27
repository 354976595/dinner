package com.foodorder.controller;

import com.foodorder.common.Result;
import com.foodorder.dto.MenuTreeVO;
import com.foodorder.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * GET /api/menu/tree
     * 返回树状菜单数据（需登录）
     */
    @GetMapping("/tree")
    public Result<List<MenuTreeVO>> getMenuTree() {
        return Result.success(menuService.getMenuTree());
    }
}
