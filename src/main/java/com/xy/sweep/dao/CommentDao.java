package com.xy.sweep.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.sweep.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容表
 * 
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
	
}
