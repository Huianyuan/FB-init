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
     * @param sessionID session
     * @param hasAccount 账号是否存在，区别注册和重置
     * @return boolean
     */
    String sendValidEmail(String email, String sessionID,boolean hasAccount);

    /**
     * 校验并注册
     *
     * @param username 用户名
     * @param password 密码
     * @Param email 邮箱
     * @Param code 验证码
     */
    String validateAndRegister(String username,String password,String email,String code,String sessionID);

    /**
     * 重置密码邮箱校验
     * @param email 邮箱地址
     * @param code 验证码
     * @param sessionID session
     * @return java.lang.String
     */
    String validateOnly(String email,String code,String sessionID);

    /**
     * 重置密码
     * @param password 密码
     * @param email 邮箱
     * @return boolean
     */
    boolean resetPassword(String password,String email);
}
