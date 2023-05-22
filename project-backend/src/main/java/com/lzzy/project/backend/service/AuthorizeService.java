package com.lzzy.project.backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author :lhy
 * @description : 登录权限校验
 * @date :2023/05/19 下午 09:36
 */
public interface AuthorizeService extends UserDetailsService {
    /**
     * 发送邮件
     *
     * @param email 邮件地址
     * @return boolean
     */
    String sendValidEmail(String email, String sessionID);

    /**
     * 校验并注册
     *
     * @param username 用户名
     * @param password 密码
     * @Param email 邮箱
     * @Param code 验证码
     */
    String validateAndRegister(String username,String password,String email,String code,String sessionID);
}
