package com.xy.sweep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.exception.RRException;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;
import com.xy.sweep.common.utils.TokenUtil;
import com.xy.sweep.dao.UserDao;
import com.xy.sweep.entity.AccountEntity;
import com.xy.sweep.entity.UserEntity;
import com.xy.sweep.entity.dto.UserDTO;
import com.xy.sweep.service.AccountService;
import com.xy.sweep.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private AccountService accountService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        UserDTO userDTO = tokenUtil.getUserJson(UserDTO.class);
        userEntity.setId(null);
        boolean flag;
        if (flag = userDTO.getUser() != null) {
            userEntity.setId(userDTO.getUser().getId());
        }
        userEntity.setAccountId(userDTO.getAccount().getId());
        userEntity.setBalance(null);
        userEntity.setDeleted(null);
        userEntity.setFootprint(null);
        userEntity.setCreateTime(null);
        userEntity.setUpdateTime(null);
        saveOrUpdate(userEntity);
        if (!flag) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setId(userDTO.getAccount().getId());
            accountEntity.setUserId(userEntity.getId());
            accountService.updateById(accountEntity);
        }
        userDTO.setUser(userEntity);
        tokenUtil.setUser(userDTO);
    }

    @Override
    public UserEntity recharge(String code) {
        if (!checkCode(code)) {
            throw new RRException("充值码无效");
        }
        int amount = Integer.parseInt(code.substring(code.length() - 4));

        UserDTO dto = tokenUtil.getUserJson(UserDTO.class);
        UserEntity userEntity = getById(dto.getUser().getId());
        userEntity.setBalance(userEntity.getBalance()+amount);
        baseMapper.recharge(userEntity.getId(), amount);
        dto.setUser(userEntity);
        tokenUtil.setUser(dto);
        return userEntity;
    }

    private boolean checkCode(String code) {
        if (code != null && code.length() == 8) {
            try {
                Integer.parseInt(code.substring(code.length() - 4));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }
}