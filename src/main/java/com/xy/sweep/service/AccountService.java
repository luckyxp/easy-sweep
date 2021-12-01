package com.xy.sweep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.entity.AccountEntity;
import com.xy.sweep.entity.dto.ResetPwdDTO;

import java.util.Map;

/**
 * 账号表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 19:22:51
 */
public interface AccountService extends IService<AccountEntity> {

    PageUtils queryPage(Map<String, Object> params);

    AccountEntity login(AccountEntity account);

    AccountEntity register(AccountEntity account);

    AccountEntity resetPassword(ResetPwdDTO dto);
}

