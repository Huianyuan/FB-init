package com.lzzy.project.backend.controller;

import com.lzzy.project.backend.entity.RestBean;
import com.lzzy.project.backend.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :lhy
 * @description :权限校验相关
 * @date :2023/05/21 下午 09:22
 */
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";

    @Resource
    AuthorizeService authorizeService;

    @PostMapping("/valid-email")
    public RestBean<String> validEmailAddress(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                              HttpSession session) {
        String s = authorizeService.sendValidEmail(email, session.getId());
        if (s==null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/register")
    public RestBean<String> register(
            @Pattern(regexp = USERNAME_REGEX) @RequestParam("username") String username,
            @Length(min = 6, max = 16) @RequestParam("password") String password,
            @Pattern(regexp=EMAIL_REGEX) @RequestParam("email") String email,
            @Length(min = 6, max = 6) @RequestParam("code") String code,
            HttpSession session) {
        String s = authorizeService.validateAndRegister(username, password, email, code, session.getId());
        if(s==null){
            return RestBean.success("注册成功");
        }else{
            return RestBean.failure(400,"注册失败，验证码填写错误！");
        }
    }
}
