package com.xy.sweep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
@Data
@TableName("user")
@ApiModel("用户表")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 账号id
     */
    @ApiModelProperty("账号id")
    private Long accountId;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;
    /**
     * 居住地
     */
    @ApiModelProperty("居住地")
    private String habitation;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;
    /**
     * qq
     */
    @ApiModelProperty("qq")
    private String qq;
    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String head;
    /**
     * 余额
     */
    @ApiModelProperty("余额")
    private Long balance;
    /**
     * 足迹
     */
    @ApiModelProperty("足迹")
    private String footprint;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;
    /**
     * 逻辑删除 0(逻辑已删除) 1(逻辑未删除)
     */
    @ApiModelProperty("逻辑删除 0(逻辑已删除) 1(逻辑未删除)")
    private Integer deleted;

}
