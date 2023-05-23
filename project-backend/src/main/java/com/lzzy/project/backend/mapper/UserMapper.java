package com.lzzy.project.backend.mapper;

import com.lzzy.project.backend.entity.auth.Account;
import com.lzzy.project.backend.entity.user.AccountUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author lhy
 * @description : User mapper
 * @date 2023年05月19日 下午 09:40
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名或邮箱查询账户 SecurityConfiguration所需
     * @param text 求情参数
     * @return com.lzzy.project.backend.entity.auth.Account
     */
    @Select("SELECT * FROM db_account WHERE username=#{username} OR email=#{email}")
    Account findAccountByNameOrEmail(String text);

    /**
     * 根据用户名或邮箱查询用户信息 AuthorizeInterceptor所需
     * @param text 请求参数
     * @return com.lzzy.project.backend.entity.user.AccountUser
     */
    @Select("SELECT * FROM db_account WHERE username=#{username} OR email=#{email}")
    AccountUser findAccountUserByNameOrEmail(String text);

    /**
     * 插入用户数据
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return int
     */
    @Insert("INSERT INTO db_account (username, password,email) VALUES (#{username}, #{password}, #{email})")
    int createAccount(String username, String password,String email);

    /**
     * 重置密码
     * @param password 新密码
     * @param email 邮箱
     * @return int
     */
    @Update("UPDATE db_account SET password=#{password} WHERE email=#{email}")
    int resetPasswordPyEmail(String password, String email);
}
