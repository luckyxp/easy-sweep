package com.xy.sweep.controller;

import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.R;
import com.xy.sweep.entity.HallEntity;
import com.xy.sweep.entity.dto.HallAudit;
import com.xy.sweep.entity.dto.HallUpdate;
import com.xy.sweep.service.HallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 纪念馆表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 13:03:32
 */
@Api(tags = "纪念馆相关API")
@RestController
@RequestMapping("sweep/hall")
public class HallController {
    @Autowired
    private HallService hallService;

    @ApiOperation("搜索")
    @GetMapping("/search")
    public R search(@ApiParam("传分页参数(page,limit)和key(关键字)") @RequestParam(required = false) Map<String, Object> params) {
        PageUtils page = hallService.search(params);
        if (page == null) {
            return R.ok("没有结果");
        }
        return R.ok().put("page", page);
    }

    @ApiOperation("信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        HallEntity hall = hallService.getById(id);

        return R.ok().put("hall", hall);
    }

    @ApiOperation("建馆")
    @PostMapping("/createHall")
    public R createHall(@RequestBody HallEntity hall) {
        HallEntity r = hallService.createHall(hall);
        return R.ok().put("data",r);
    }

    @ApiOperation("根据id修改信息")
    @PutMapping("/updateInfoById")
    public R updateInfoById(@RequestBody HallUpdate hallUpdate) {
        hallService.updateInfoById(hallUpdate);
        return R.ok();
    }


    @ApiOperation("最新建馆")
    @GetMapping("/newCreate")
    public R newCreate() {
        List<HallEntity> newCreate = hallService.newCreate();
        return R.ok().put("data", newCreate);
    }

    @ApiOperation("审核")
    @PostMapping("/audit")
    public R audit(@RequestBody HallAudit hallAudit) {
        hallService.audit(hallAudit);
        return R.ok();
    }

    @ApiOperation("指数排行")
    @GetMapping("/rank")
    public R rank(@RequestParam String key) {
        List<HallEntity> rank = hallService.rank(key);
        return R.ok().put("data", rank);
    }

    @ApiOperation("删馆")
    @GetMapping("/remove/{id}")
    public R deleteById(@PathVariable("id") Long id) {
        hallService.deleteById(id);
        return R.ok();
    }

    @ApiOperation("我的相关纪念馆(type 0创建,1关注,2亲友")
    @GetMapping("/myHall")
    public R myHall(@RequestParam(required = false) Integer type) {
        List<HallEntity> hallEntities = hallService.myHall(type);
        return R.ok().put("data", hallEntities);
    }

}
