package com.foodorder.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderVO {
    private Long id;
    private String orderNo;
    private String tableNo;
    private String remark;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<ItemVO> items;

    @Data
    public static class ItemVO {
        private Long dishId;
        private String dishName;
        private BigDecimal price;
        private Integer count;
        private BigDecimal subtotal;
    }
}
