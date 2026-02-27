package com.foodorder.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_order")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private String tableNo;
    private String remark;
    private BigDecimal totalAmount;
    private String status;   // PENDING / COMPLETED / CANCELLED

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
