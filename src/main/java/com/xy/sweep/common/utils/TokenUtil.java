package com.xy.sweep.common.utils;

import com.alibaba.fastjson.JSON;
import com.xy.sweep.common.exception.RRException;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.UUID;

/**
 * @author climb.xu
 * @date 2021/11/30 19:28
 */
@Component
public class TokenUtil {
    public String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private final char[] hexCode = "0123456789abcdef".toCharArray();
    @Resource
    private HttpServletRequest request;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    //生成Token值
    public String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new RRException("生成Token失败", e);
        }
    }

    public String takeToken() {
        String accessToken = request.getParameter("accessToken");
        if (StringUtils.isBlank(accessToken)) {
            accessToken = request.getHeader("Authorization");
            if (StringUtils.isBlank(accessToken)) {
                accessToken = (String) request.getSession().getAttribute("token");
            }
        }
        return accessToken;
    }

    public <T> T getUserJson(Class<T> tClass) {
        String token = takeToken();
        if (token != null) {
            String s = redisTemplate.opsForValue().get(token).toString();
            return JSON.parseObject(s, tClass);
        }
        return null;
    }

    public void setUser(Object object) {
        String token = takeToken();
        if (token != null) {
            redisTemplate.opsForValue().set(token, object, Duration.ofDays(3L));
        }
    }
}
