package com.foodorder.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.foodorder.common.exception.BizException;
import com.foodorder.dto.RechargeRequest;
import com.foodorder.entity.Recharge;
import com.foodorder.entity.User;
import com.foodorder.mapper.RechargeMapper;
import com.foodorder.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final RechargeMapper rechargeMapper;

    public User getUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) throw new BizException("用户不存在");
        user.setPassword(null);
        return user;
    }

    @Transactional
    public User recharge(Long userId, RechargeRequest req) {
        User user = userMapper.selectById(userId);
        if (user == null) throw new BizException("用户不存在");

        BigDecimal gift = req.getGift() != null ? req.getGift() : BigDecimal.ZERO;
        BigDecimal addAmount = req.getAmount().add(gift);

        userMapper.update(null,
                new LambdaUpdateWrapper<User>()
                        .eq(User::getId, userId)
                        .setSql("balance = balance + " + addAmount)
        );

        // 记录充值日志
        Recharge recharge = new Recharge();
        recharge.setUserId(userId);
        recharge.setAmount(req.getAmount());
        recharge.setGift(gift);
        recharge.setRemark(req.getRemark());
        recharge.setCreatedAt(LocalDateTime.now());
        rechargeMapper.insert(recharge);

        return getUser(userId);
    }
}
