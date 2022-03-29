package com.xy.sweep.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author climb.xu
 * @date 2022/3/27 17:04
 */
@Data
@ApiModel("注册信息")
public class RegisterDTO {
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;
    /**
     * 密码
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 验证码
     */
    @ApiModelProperty("验证码")
    private String code;
}
