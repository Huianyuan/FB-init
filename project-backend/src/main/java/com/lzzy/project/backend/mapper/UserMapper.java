package com.lzzy.project.backend.mapper;

import com.lzzy.project.backend.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author lhy
 * @description :TODO
 * @date 2023年05月19日 下午 09:40
 */
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM db_account WHERE username=#{username} OR email=#{email}")
    Account findAccountByNameOrEmail(String text);
}
