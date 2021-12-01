package com.xy.sweep.controller;

import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.R;
import com.xy.sweep.entity.SacrificeEntity;
import com.xy.sweep.service.SacrificeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 祭品表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 13:03:32
 */
@Api(tags = "祭品相关API")
@RestController
@RequestMapping("sweep/sacrifice")
public class SacrificeController {
    @Autowired
    private SacrificeService sacrificeService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = sacrificeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SacrificeEntity sacrifice = sacrificeService.getById(id);

        return R.ok().put("sacrifice", sacrifice);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    public R save(@RequestBody SacrificeEntity sacrifice){
		sacrificeService.save(sacrifice);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PutMapping("/update")
    public R update(@RequestBody SacrificeEntity sacrifice){
		sacrificeService.updateById(sacrifice);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		sacrificeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
