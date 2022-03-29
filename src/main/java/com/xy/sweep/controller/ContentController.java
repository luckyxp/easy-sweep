package com.xy.sweep.controller;

import com.xy.sweep.common.utils.R;
import com.xy.sweep.entity.ContentEntity;
import com.xy.sweep.entity.dto.ContentPubDTO;
import com.xy.sweep.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 内容表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 13:03:32
 */
@Api(tags = "内容相关API")
@RestController
@RequestMapping("sweep/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    /**
     * 信息
     */
    @ApiOperation("信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ContentEntity content = contentService.getById(id);
        return R.ok().put("content", content);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PutMapping("/update")
    public R update(@RequestBody ContentEntity content) {
        contentService.updateById(content);
        return R.ok();
    }
    /**
     * 删除
     */
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        contentService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    @ApiOperation("根据纪念馆id获取文章")
    @GetMapping("/listByHallId/{hallId}")
    public R listByHallId(@PathVariable("hallId") Long hallId) {
        List<ContentEntity> contents = contentService.listByHallId(hallId);
        return R.ok().put("data", contents);
    }

    @ApiOperation("我的文章")
    @GetMapping("/myContent")
    public R myContent() {
        List<ContentEntity> contents = contentService.myContent();
        return R.ok().put("data", contents);
    }

    @ApiOperation("发布文章")
    @PostMapping("/pubContent")
    public R publish(@RequestBody ContentPubDTO dto) {
        ContentEntity result = contentService.publish(dto);
        return R.ok().put("data",result);
    }
}
