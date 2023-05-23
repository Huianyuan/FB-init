package com.lzzy.project.backend.interceptor;

import com.lzzy.project.backend.entity.user.AccountUser;
import com.lzzy.project.backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author :lhy
 * @description :不登录无法到达拦截器
 * @date :2023/05/23 下午 08:58
 */
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {


    @Resource
    UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        AccountUser accountUser = userMapper.findAccountUserByNameOrEmail(username);
        httpServletRequest.getSession().setAttribute("account", accountUser);
        return true;
    }
}
