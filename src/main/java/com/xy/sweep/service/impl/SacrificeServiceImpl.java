package com.xy.sweep.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;

import com.xy.sweep.dao.SacrificeDao;
import com.xy.sweep.entity.SacrificeEntity;
import com.xy.sweep.service.SacrificeService;


@Service("sacrificeService")
public class SacrificeServiceImpl extends ServiceImpl<SacrificeDao, SacrificeEntity> implements SacrificeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SacrificeEntity> page = this.page(
                new Query<SacrificeEntity>().getPage(params),
                new QueryWrapper<SacrificeEntity>()
        );

        return new PageUtils(page);
    }

}