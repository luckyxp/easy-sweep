package com.xy.sweep.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author climb.xu
 * @date 2021/12/1 14:50
 */
@Data
@ApiModel("修改密码")
public class ResetPwdDTO {
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("原密码")
    private String oldPwd;
    @ApiModelProperty("新密码")
    private String newPwd;
}
