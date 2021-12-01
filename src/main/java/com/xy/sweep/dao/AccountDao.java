package com.xy.sweep.dao;

import com.xy.sweep.entity.AccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号表
 * 
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 19:22:51
 */
@Mapper
public interface AccountDao extends BaseMapper<AccountEntity> {
	
}
