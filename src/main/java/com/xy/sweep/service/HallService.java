package com.xy.sweep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.entity.HallEntity;
import com.xy.sweep.entity.dto.HallAudit;
import com.xy.sweep.entity.dto.HallUpdate;

import java.util.List;
import java.util.Map;

/**
 * 纪念馆表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
public interface HallService extends IService<HallEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<HallEntity> newCreate();

    List<HallEntity> rank(String key);

    HallEntity createHall(HallEntity hall);

    void audit(HallAudit hallAudit);

    void updateInfoById(HallUpdate hallUpdate);

    void deleteById(Long id);

    PageUtils search(Map<String, Object> params);

    List<HallEntity> myHall(Integer type);

    Long getDeceasedIdByHallId(Long hallId);
}

