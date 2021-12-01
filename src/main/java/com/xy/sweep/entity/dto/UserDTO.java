package com.xy.sweep.entity.dto;

import com.xy.sweep.entity.AccountEntity;
import com.xy.sweep.entity.UserEntity;
import lombok.Data;

/**
 * @author climb.xu
 * @date 2021/12/1 15:42
 */
@Data
public class UserDTO {
    private AccountEntity account;
    private UserEntity user;

    public UserDTO() {

    }
    public UserDTO(AccountEntity account, UserEntity user) {
        this.account = account;
        this.user = user;
    }
}
