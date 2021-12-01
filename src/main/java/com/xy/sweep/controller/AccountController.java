package com.xy.sweep.controller;

import com.alibaba.fastjson.JSON;
import com.xy.sweep.common.utils.R;
import com.xy.sweep.common.utils.TokenUtil;
import com.xy.sweep.entity.AccountEntity;
import com.xy.sweep.entity.UserEntity;
import com.xy.sweep.entity.dto.ResetPwdDTO;
import com.xy.sweep.entity.dto.UserDTO;
import com.xy.sweep.service.AccountService;
import com.xy.sweep.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


/**
 * 账号表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 19:22:51
 */
@Api(tags = "账户相关API")
@RestController
@RequestMapping("sweep/account")
public class AccountController {
    @Resource
    private AccountService accountService;
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    TokenUtil tokenUtil;

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(@RequestBody AccountEntity account, HttpServletRequest request) {
        AccountEntity accountEntity = accountService.login(account);
        if (accountEntity == null) {
            return R.error("用户名或密码错误");
        }
        UserEntity user = userService.getById(accountEntity.getUserId());
        UserDTO userDTO = new UserDTO(accountEntity, user);
        String token = tokenUtil.takeToken();
        boolean flag;
        if (flag = !StringUtils.isBlank(token)) {
            Object str = redisTemplate.opsForValue().get(token);
            if (flag = str != null) {
                UserDTO dto = JSON.parseObject(str.toString(), UserDTO.class);
                if (flag = dto != null) {
                    if (flag = accountEntity.getId().equals(dto.getAccount().getId())) {
                        redisTemplate.expire(token, Duration.ofDays(3L));//重复登录,将token有效期延长
                    }
                }
            }
        }
        if (!flag) {
            token = tokenUtil.generateValue();
            Object t = redisTemplate.opsForValue().get(accountEntity.getId()+"");
            if (t != null) {
                redisTemplate.delete(t.toString());
            }
            redisTemplate.opsForValue().set(token, userDTO, Duration.ofDays(3L));
            redisTemplate.opsForValue().set(accountEntity.getId() + "", token);
        }
        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("token", token);
        map.put("user", user);
        return R.ok(map);
    }

    @ApiOperation("注销")
    @PostMapping("/logout")
    public R logout() {
        String token = tokenUtil.takeToken();
        Boolean result = null;
        if (token != null) {
            result = redisTemplate.delete(token);
        }
        if (result == null || !result) {
            return R.error("您还没有登录");
        }
        return R.ok("注销成功");
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public R register(@RequestBody AccountEntity account) {
        AccountEntity accountEntity = accountService.register(account);
        return R.ok().put("data", accountEntity);
    }

    @ApiOperation("修改密码")
    @PostMapping("/resetPassword")
    public R resetPassword(@RequestBody ResetPwdDTO dto) {
        AccountEntity accountEntity = accountService.resetPassword(dto);
        return R.ok().put("data", accountEntity);
    }


}
