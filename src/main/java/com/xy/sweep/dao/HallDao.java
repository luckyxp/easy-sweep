package com.xy.sweep.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.sweep.entity.HallEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 纪念馆表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
@Mapper
public interface HallDao extends BaseMapper<HallEntity> {

    List<HallEntity> newCreate();

    List<HallEntity> rank(String key);

    List<HallEntity> selectByUserName(String key);

    List<HallEntity> myHall(@Param("userId") Long userId, @Param("type") Integer type);

    Long getDeceasedIdByHallId(Long hallId);
}
