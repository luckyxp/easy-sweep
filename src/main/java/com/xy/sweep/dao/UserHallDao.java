package com.xy.sweep.dao;

import com.xy.sweep.entity.UserHallEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户-纪念馆关联表
 * 
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:49
 */
@Mapper
public interface UserHallDao extends BaseMapper<UserHallEntity> {
	
}
