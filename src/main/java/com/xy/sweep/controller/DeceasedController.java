package com.xy.sweep.controller;

import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.R;
import com.xy.sweep.entity.DeceasedEntity;
import com.xy.sweep.service.DeceasedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 逝者表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 13:03:31
 */
@Api(tags = "逝者相关API")
@RestController
@RequestMapping("sweep/deceased")
public class DeceasedController {
    @Autowired
    private DeceasedService deceasedService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params) {
        PageUtils page = deceasedService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        DeceasedEntity deceased = deceasedService.getById(id);

        return R.ok().put("deceased", deceased);
    }


    /**
     * 修改
     */
    @ApiOperation("修改")
    @PutMapping("/update")
    public R update(@RequestBody DeceasedEntity deceased) {
        deceasedService.updateById(deceased);

        return R.ok();
    }


}
