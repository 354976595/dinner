package com.foodorder.config;

import com.foodorder.common.exception.BizException;
import com.foodorder.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                String token = request.getHeader("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }
                if (token == null || !jwtUtil.validateToken(token)) {
                    throw new BizException(401, "未登录或登录已过期");
                }
                Long userId = jwtUtil.getUserId(token);
                request.setAttribute("userId", userId);
                return true;
            }
        })
        .addPathPatterns("/api/**")
        .excludePathPatterns("/api/auth/**");
    }
}
