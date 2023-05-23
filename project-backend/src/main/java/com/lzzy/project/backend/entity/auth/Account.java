package com.lzzy.project.backend.entity.auth;

import lombok.Data;

/**
 * @author :lhy
 * @description :用户表 db_account
 * @date :2023/05/19 下午 09:37
 */
@Data
public class Account {

    int id;
    String username;
    String password;
    String email;



}
