package com.xy.sweep.email.service;

import com.xy.sweep.common.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author climb.xu
 * @date 2022/3/27 15:33
 */
@Slf4j
@Service
public class EmailCodeService {
    public static final String REGISTER = "register";
    public static final String FORGET = "forget";
    public static final String LOGIN = "login";

    @Value("${spring.mail.nickname}")
    private String nickname;
    @Value("${spring.mail.username}")
    private String username;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private RedisTemplate<String, String> redisTemplate;


    private void sendCode(String code, String target, String template) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("验证码");
        message.setText(template.replace("${code}", code));
        message.setFrom(nickname + "<" + username + ">");
        message.setTo(target);
        mailSender.send(message);
    }
    @Async("threadPool")
    public void sendMsg(String msg, String target) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("easy-sweep");
        message.setText(msg);
        message.setFrom(nickname + "<" + username + ">");
        message.setTo(target);
        mailSender.send(message);
    }

    public String getCode(String target, String template, String type) {
        if (target == null) {
            throw new RRException("邮箱不能为空!");
        }
        if (!checkEmail(target)) {
            throw new RRException("邮箱格式错误!");
        }
        Long ttl = redisTemplate.getExpire(type + target);
        if (ttl != null && ttl > 0) {
            throw new RRException("请在" + ttl + "秒后再试!");
        }
        String t = System.currentTimeMillis() + "";
        String code = t.substring(t.length() - 4);
        redisTemplate.opsForValue().set(type + target, code, Duration.ofSeconds(60));
        sendCode(code, target, template);
        return code;
    }

    public Boolean checkCode(String target, String code, String type) {
        if (target == null || code == null) {
            throw new RRException("邮箱或验证码不能为空!");
        }
        if (!checkEmail(target)) {
            throw new RRException("邮箱格式错误!");
        }
        boolean result = false;
        String c = redisTemplate.opsForValue().get(type + target);
        if (c != null && c.equals(code)) {
            result = true;
            redisTemplate.delete(type + target);
        }
        return result;
    }

    private boolean checkEmail(String target) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(target);
        return matcher.matches();
    }
}
