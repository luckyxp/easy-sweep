package com.xy.sweep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.entity.ContentEntity;
import com.xy.sweep.entity.dto.ContentPubDTO;

import java.util.List;
import java.util.Map;

/**
 * 内容表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
public interface ContentService extends IService<ContentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ContentEntity> listByHallId(Long hallId);

    List<ContentEntity> myContent();

    ContentEntity publish(ContentPubDTO dto);
}

