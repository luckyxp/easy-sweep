package com.xy.sweep.interceptor;

import com.xy.sweep.annotation.Ignore;
import com.xy.sweep.common.exception.RRException;
import com.xy.sweep.common.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author climb.xu
 * @date 2021/11/30 18:44
 * 登录拦截器
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private static final String OPTIONS = "OPTIONS";
    @Resource
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行options请求
        if (request.getMethod().toUpperCase().equals(OPTIONS)) {
            passOptions(response);
            return false;
        }
        Method method = null;
        if (handler instanceof HandlerMethod) {
            method = ((HandlerMethod) handler).getMethod();
        }
        // 检查是否忽略权限验证
        if (method == null || checkIgnore(method)) {
            return true;
        }
        // 获取token
        String accessToken = tokenUtil.takeToken();
        if (StringUtils.isBlank(accessToken)) {
            throw new RRException("Token不能为空", 1001);
        }
        Boolean result = redisTemplate.hasKey(accessToken);
        if (result == null || !result) {
            throw new RRException("Token错误", 1002);
        }
        return true;
    }

    /**
     * 放行options请求
     */
    public static void passOptions(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
    }

    /**
     * 检查是否忽略权限
     */
    public static boolean checkIgnore(Method method) {
        Ignore annotation = method.getAnnotation(Ignore.class);
        // 方法上没有注解再检查类上面有没有注解
        if (annotation == null) {
            annotation = method.getDeclaringClass().getAnnotation(Ignore.class);
            return annotation != null;
        }
        return true;
    }
}