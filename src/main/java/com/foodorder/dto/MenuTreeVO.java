package com.foodorder.dto;

import com.foodorder.entity.Dish;
import lombok.Data;
import java.util.List;

@Data
public class MenuTreeVO {
    private Long id;
    private String name;
    private List<SubCategoryVO> children;

    @Data
    public static class SubCategoryVO {
        private Long id;
        private String name;
        private List<DishVO> items;
    }

    @Data
    public static class DishVO {
        private Long id;
        private String name;
        private String desc;
        private double price;
        private String image;
        private int count;

        public static DishVO fromDish(Dish d) {
            DishVO vo = new DishVO();
            vo.setId(d.getId());
            vo.setName(d.getName());
            vo.setDesc(d.getDescription());
            vo.setPrice(d.getPrice().doubleValue());
            vo.setImage(d.getImage());
            vo.setCount(0);
            return vo;
        }
    }
}
