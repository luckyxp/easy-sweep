package com.xy.sweep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.entity.UserHallEntity;

import java.util.Map;

/**
 * 用户-纪念馆关联表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:49
 */
public interface UserHallService extends IService<UserHallEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

