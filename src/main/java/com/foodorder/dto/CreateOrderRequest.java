package com.foodorder.dto;

import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderRequest {

    private String tableNo;
    private String remark;

    @NotEmpty(message = "请至少选择一道菜")
    @Valid
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO {
        private Long dishId;
        private String dishName;
        private BigDecimal price;
        private Integer count;
    }
}
