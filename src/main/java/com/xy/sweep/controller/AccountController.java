package com.xy.sweep.controller;

import com.xy.sweep.common.utils.R;
import com.xy.sweep.entity.AccountEntity;
import com.xy.sweep.entity.dto.RegisterDTO;
import com.xy.sweep.entity.dto.ResetPwdDTO;
import com.xy.sweep.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(@RequestBody AccountEntity account, HttpServletRequest request) {
        Map<String, Object> map = accountService.login(account, request);
        return R.ok(map);
    }

    @ApiOperation("邮箱登录")
    @GetMapping("/loginForEmail")
    public R loginForEmail(@RequestParam("email") String email, @RequestParam("code") String code, HttpServletRequest request) {
        Map<String, Object> map = accountService.loginForEmail(email, code, request);
        return R.ok(map);
    }

    @ApiOperation("注销")
    @GetMapping("/logout")
    public R logout() {
        accountService.logout();
        return R.ok("注销成功");
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterDTO registerDTO) {
        AccountEntity accountEntity = accountService.register(registerDTO);
        return R.ok().put("data", accountEntity);
    }

    @ApiOperation("修改密码")
    @PostMapping("/resetPassword")
    public R resetPassword(@RequestBody ResetPwdDTO dto) {
        AccountEntity accountEntity = accountService.resetPassword(dto);
        return R.ok().put("data", accountEntity);
    }

    @ApiOperation("忘记密码")
    @PostMapping("/forgetPassword")
    public R forgetPassword(@RequestBody RegisterDTO registerDTO) {
        AccountEntity accountEntity = accountService.forgetPassword(registerDTO);
        return R.ok().put("data", accountEntity);
    }


}
