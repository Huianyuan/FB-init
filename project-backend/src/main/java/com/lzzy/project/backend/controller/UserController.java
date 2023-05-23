package com.lzzy.project.backend.controller;

import com.lzzy.project.backend.entity.RestBean;
import com.lzzy.project.backend.entity.user.AccountUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author :lhy
 * @description :userController
 * @date :2023/05/23 下午 09:02
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/me")
    public RestBean<AccountUser> getMe(@SessionAttribute("account") AccountUser user) {
        return RestBean.success(user);
    }

}
