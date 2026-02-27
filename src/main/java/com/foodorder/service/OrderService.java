package com.foodorder.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foodorder.common.exception.BizException;
import com.foodorder.dto.CreateOrderRequest;
import com.foodorder.dto.OrderVO;
import com.foodorder.entity.Order;
import com.foodorder.entity.OrderItem;
import com.foodorder.mapper.OrderItemMapper;
import com.foodorder.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    public OrderVO createOrder(Long userId, CreateOrderRequest req) {
        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new BizException("订单明细不能为空");
        }

        // 计算总价
        BigDecimal total = req.getItems().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 生成订单号
        String orderNo = "ORD" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + userId;

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTableNo(req.getTableNo());
        order.setRemark(req.getRemark());
        order.setTotalAmount(total);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        orderMapper.insert(order);

        // 插入明细
        List<OrderItem> items = req.getItems().stream().map(i -> {
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setDishId(i.getDishId());
            item.setDishName(i.getDishName());
            item.setPrice(i.getPrice());
            item.setCount(i.getCount());
            item.setSubtotal(i.getPrice().multiply(BigDecimal.valueOf(i.getCount())));
            return item;
        }).collect(Collectors.toList());
        items.forEach(orderItemMapper::insert);

        return buildVO(order, items);
    }

    public List<OrderVO> getUserOrders(Long userId) {
        List<Order> orders = orderMapper.selectList(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getUserId, userId)
                        .orderByDesc(Order::getCreatedAt)
        );
        if (orders.isEmpty()) return List.of();

        List<Long> orderIds = orders.stream().map(Order::getId).collect(Collectors.toList());
        List<OrderItem> allItems = orderItemMapper.selectList(
                new LambdaQueryWrapper<OrderItem>().in(OrderItem::getOrderId, orderIds)
        );
        Map<Long, List<OrderItem>> itemMap = allItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderId));

        return orders.stream()
                .map(o -> buildVO(o, itemMap.getOrDefault(o.getId(), List.of())))
                .collect(Collectors.toList());
    }

    private OrderVO buildVO(Order order, List<OrderItem> items) {
        OrderVO vo = new OrderVO();
        vo.setId(order.getId());
        vo.setOrderNo(order.getOrderNo());
        vo.setTableNo(order.getTableNo());
        vo.setRemark(order.getRemark());
        vo.setTotalAmount(order.getTotalAmount());
        vo.setStatus(order.getStatus());
        vo.setCreatedAt(order.getCreatedAt());
        vo.setItems(items.stream().map(i -> {
            OrderVO.ItemVO iv = new OrderVO.ItemVO();
            iv.setDishId(i.getDishId());
            iv.setDishName(i.getDishName());
            iv.setPrice(i.getPrice());
            iv.setCount(i.getCount());
            iv.setSubtotal(i.getSubtotal());
            return iv;
        }).collect(Collectors.toList()));
        return vo;
    }
}
