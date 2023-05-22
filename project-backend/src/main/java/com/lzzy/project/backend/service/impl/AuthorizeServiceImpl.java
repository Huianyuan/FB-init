package com.lzzy.project.backend.service.impl;

import com.lzzy.project.backend.entity.Account;
import com.lzzy.project.backend.mapper.UserMapper;
import com.lzzy.project.backend.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author :lhy
 * @description : 登录权限校验
 * @date :2023/05/19 下午 09:36
 */
@Service
public class AuthorizeServiceImpl implements AuthorizeService{
    @Value("${spring.mail.username}")
    String fromEmail;
    @Resource
    UserMapper mapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Resource
    MailSender mailSender;

    // 使用注入会产生循坏引用
    // @Resource
    // BCryptPasswordEncoder encoder;
    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username==null)
            throw new UsernameNotFoundException("用户名不能为空");
        Account account = mapper.findAccountByNameOrEmail(username);
        if(account==null)
            throw new UsernameNotFoundException("用户名或密码错误");
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }

    @Override
    public String sendValidEmail(String email, String sessionID) {

        //判断当前邮箱地址是否在redis，存在则发送消息，不存在则存储当前地址，设置1分钟过期时间
        if(Boolean.TRUE.equals(redisTemplate.hasKey(email))){
            return "请勿重复获取验证码，请1分钟后重试";
        }
        redisTemplate.opsForValue().set("email", String.valueOf(email), 1, TimeUnit.MINUTES);

        String key = "email:" + sessionID + ":" + email;
        // 4.过期时间3分钟，如果此时重新要求发邮件，只要剩余时间低于2分钟，就可以重新发送一次，重复此流程
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            Long expire = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) {
                return "请求频繁，请稍后再试。";
            }
        }
        if (mapper.findAccountByNameOrEmail(email) != null) {
            return "此邮箱已被其他用户使用，请换新邮箱重试！";
        }
        // 1.生成对应的验证码
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("验证邮件");
        message.setText("您的验证码是：" + code);
        // 2，发送验证码到指定邮箱
        try {
            mailSender.send(message);
            // 3，把邮箱和对应的验证码直接放到Redis里面
            redisTemplate.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        } catch (MailException e) {
            e.printStackTrace();
            return "邮件发送失败，请检查邮件地址是否有效！";
        }
    }

    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionID) {
        String key = "email:" + sessionID + ":" + email;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            String s = redisTemplate.opsForValue().get(key);
            if (s == null) return "验证码失效，请重新请求";
            if (s.equals(code)) {
                password = encoder.encode(password);
                if(mapper.createAccount(username, password, email)>0){
                    return null;
                }else{
                    return "内部错误，请联系管理员";
                }
            } else {
                return "验证码错误，请重新请求";
            }
        } else {
            return "请先请求一封验证邮件";
        }
    }
}
