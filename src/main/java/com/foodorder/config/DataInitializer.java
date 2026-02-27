package com.foodorder.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foodorder.entity.User;
import com.foodorder.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 更新初始用户密码为正确的 BCrypt hash（123456）
        updatePassword("admin");
        updatePassword("user1");
        log.info("初始化用户密码完成");
    }

    private void updatePassword(String username) {
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
                        .select(User::getId, User::getUsername, User::getPassword)
        );
        if (user != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode("123456"));
            userMapper.updateById(user);
        } else if (user == null) {
            log.warn("用户 {} 不存在，跳过密码初始化", username);
        }
    }
}
