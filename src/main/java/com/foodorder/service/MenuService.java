package com.foodorder.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foodorder.dto.MenuTreeVO;
import com.foodorder.entity.Category;
import com.foodorder.entity.Dish;
import com.foodorder.mapper.CategoryMapper;
import com.foodorder.mapper.DishMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final CategoryMapper categoryMapper;
    private final DishMapper dishMapper;

    public List<MenuTreeVO> getMenuTree() {
        // 查所有分类
        List<Category> allCategories = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>().orderByAsc(Category::getSort)
        );

        // 查所有在售菜品
        List<Dish> allDishes = dishMapper.selectList(
                new LambdaQueryWrapper<Dish>()
                        .eq(Dish::getStatus, 1)
                        .orderByAsc(Dish::getSort)
        );

        // 按 categoryId 分组
        Map<Long, List<Dish>> dishMap = allDishes.stream()
                .collect(Collectors.groupingBy(Dish::getCategoryId));

        // 按 parentId 分组，parentId=0 为一级
        Map<Long, List<Category>> subMap = allCategories.stream()
                .filter(c -> c.getParentId() != 0)
                .collect(Collectors.groupingBy(Category::getParentId));

        return allCategories.stream()
                .filter(c -> c.getParentId() == 0)
                .map(first -> {
                    MenuTreeVO vo = new MenuTreeVO();
                    vo.setId(first.getId());
                    vo.setName(first.getName());

                    List<MenuTreeVO.SubCategoryVO> children = subMap
                            .getOrDefault(first.getId(), List.of())
                            .stream()
                            .map(sub -> {
                                MenuTreeVO.SubCategoryVO subVO = new MenuTreeVO.SubCategoryVO();
                                subVO.setId(sub.getId());
                                subVO.setName(sub.getName());
                                List<MenuTreeVO.DishVO> items = dishMap
                                        .getOrDefault(sub.getId(), List.of())
                                        .stream()
                                        .map(MenuTreeVO.DishVO::fromDish)
                                        .collect(Collectors.toList());
                                subVO.setItems(items);
                                return subVO;
                            })
                            .collect(Collectors.toList());
                    vo.setChildren(children);
                    return vo;
                })
                .collect(Collectors.toList());
    }
}
