package com.xy.sweep.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.sweep.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 用户表
 * 
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    @Update("update user set balance = balance+ #{amount} where id = #{id}")
    void recharge(Long id,int amount);
}
