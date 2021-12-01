package com.xy.sweep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.exception.RRException;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;
import com.xy.sweep.dao.AccountDao;
import com.xy.sweep.entity.AccountEntity;
import com.xy.sweep.entity.UserEntity;
import com.xy.sweep.entity.dto.ResetPwdDTO;
import com.xy.sweep.service.AccountService;
import com.xy.sweep.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service("accountService")
public class AccountServiceImpl extends ServiceImpl<AccountDao, AccountEntity> implements AccountService {
    @Resource
    private UserService userService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccountEntity> page = this.page(
                new Query<AccountEntity>().getPage(params),
                new QueryWrapper<AccountEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public AccountEntity login(AccountEntity account) {
        return getOne(
                new QueryWrapper<AccountEntity>()
                        .eq("account", account.getAccount())
                        .eq("password", account.getPassword()));
    }

    @Override
    public AccountEntity register(AccountEntity account) {
        if (getOne(new QueryWrapper<AccountEntity>()
                .eq("account", account.getAccount())) != null) {
            throw new RRException("账号已存在");
        }
        UserEntity userEntity = new UserEntity();
        userService.save(userEntity);
        account.setAccount(null);
        account.setUserId(userEntity.getId());
        save(account);
        userEntity.setAccountId(account.getId());
        userService.updateById(userEntity);
        return account;
    }


    @Override
    public AccountEntity resetPassword(ResetPwdDTO dto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount(dto.getAccount());
        accountEntity.setPassword(dto.getOldPwd());
        AccountEntity login = login(accountEntity);
        if (login != null) {
            login.setPassword(dto.getNewPwd());
            updateById(login);
            return login;
        }
        throw new RRException("账号或原密码错误");
    }

}