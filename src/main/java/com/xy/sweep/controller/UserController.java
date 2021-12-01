package com.xy.sweep.controller;

import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.R;
import com.xy.sweep.common.utils.TokenUtil;
import com.xy.sweep.entity.UserEntity;
import com.xy.sweep.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;



/**
 * 用户表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 13:03:32
 */
@Api(tags = "用户相关API")
@RestController
@RequestMapping("sweep/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Resource
    TokenUtil tokenUtil;
    @ApiOperation("用户列表")
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = userService.queryPage(params);
        return R.ok().put("page", page);
    }


    @ApiOperation("根据id获取用户信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		UserEntity user = userService.getById(id);
        return R.ok().put("user", user);
    }

    @ApiOperation("修改用户信息")
    @PutMapping("/update")
    public R update(@RequestBody UserEntity user){
		userService.updateUser(user);
        return R.ok();
    }
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		userService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
    @ApiOperation("充值(充值码)")
    @PostMapping("/recharge")
    public R recharge(@RequestBody String code){
        UserEntity userEntity = userService.recharge(code);
        return R.ok().put("data", userEntity);
    }

}
