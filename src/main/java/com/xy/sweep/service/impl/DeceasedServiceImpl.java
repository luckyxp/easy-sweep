package com.xy.sweep.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;

import com.xy.sweep.dao.DeceasedDao;
import com.xy.sweep.entity.DeceasedEntity;
import com.xy.sweep.service.DeceasedService;


@Service("deceasedService")
public class DeceasedServiceImpl extends ServiceImpl<DeceasedDao, DeceasedEntity> implements DeceasedService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DeceasedEntity> page = this.page(
                new Query<DeceasedEntity>().getPage(params),
                new QueryWrapper<DeceasedEntity>()
        );

        return new PageUtils(page);
    }

}