package com.xy.sweep.controller;

import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.R;
import com.xy.sweep.entity.UserHallEntity;
import com.xy.sweep.service.UserHallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 用户-纪念馆关联表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 13:03:32
 */
@Api(tags = "用户-纪念馆关联相关API")
@RestController
@RequestMapping("sweep/userhall")
public class UserHallController {
    @Autowired
    private UserHallService userHallService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = userHallService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		UserHallEntity userHall = userHallService.getById(id);

        return R.ok().put("userHall", userHall);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    public R save(@RequestBody UserHallEntity userHall){
		userHallService.save(userHall);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PutMapping("/update")
    public R update(@RequestBody UserHallEntity userHall){
		userHallService.updateById(userHall);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		userHallService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
