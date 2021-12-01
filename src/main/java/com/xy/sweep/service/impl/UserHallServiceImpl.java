package com.xy.sweep.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;

import com.xy.sweep.dao.UserHallDao;
import com.xy.sweep.entity.UserHallEntity;
import com.xy.sweep.service.UserHallService;


@Service("userHallService")
public class UserHallServiceImpl extends ServiceImpl<UserHallDao, UserHallEntity> implements UserHallService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserHallEntity> page = this.page(
                new Query<UserHallEntity>().getPage(params),
                new QueryWrapper<UserHallEntity>()
        );

        return new PageUtils(page);
    }

}