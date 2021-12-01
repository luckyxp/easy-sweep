package com.xy.sweep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.entity.DeceasedEntity;

import java.util.Map;

/**
 * 逝者表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
public interface DeceasedService extends IService<DeceasedEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

