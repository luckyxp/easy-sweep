package com.xy.sweep.email.controller;

import com.xy.sweep.email.service.EmailCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author climb.xu
 * @date 2022/3/27 16:51
 */
@Api(tags = "邮箱验证码")
@RestController
@RequestMapping("sweep/emailCode")
public class EmailCodeController {
    @Resource
    private EmailCodeService emailCodeService;

    @ApiOperation("获取验证码")
    @GetMapping("/sendCode")
    public void sendCode(@RequestParam("email") String email, @ApiParam(value = "0注册,1忘记密码,2登录", example = "0") @RequestParam("type") Integer type) throws Exception {
        String t = EmailCodeService.REGISTER;
        if (type == 1) {
            t = EmailCodeService.FORGET;
        } else if (type == 2) {
            t = EmailCodeService.LOGIN;
        }
        emailCodeService.getCode(email, "您好,欢迎使用easy-sweep云扫墓平台,您的验证码是 ${code},1分钟内有效", t);
    }
}
