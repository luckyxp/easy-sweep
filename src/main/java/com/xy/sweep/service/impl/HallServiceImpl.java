package com.xy.sweep.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;

import com.xy.sweep.dao.HallDao;
import com.xy.sweep.entity.HallEntity;
import com.xy.sweep.service.HallService;


@Service("hallService")
public class HallServiceImpl extends ServiceImpl<HallDao, HallEntity> implements HallService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HallEntity> page = this.page(
                new Query<HallEntity>().getPage(params),
                new QueryWrapper<HallEntity>()
        );

        return new PageUtils(page);
    }

}