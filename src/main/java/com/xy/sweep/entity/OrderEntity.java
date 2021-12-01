package com.xy.sweep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户流水账目表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:49
 */
@Data
@TableName("t_order")
@ApiModel("用户流水账目表")
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 金额
     */
    @ApiModelProperty("金额")
    private Long amount;
    /**
     * 逝者id
     */
    @ApiModelProperty("逝者id")
    private Long deceasedId;
    /**
     * 类型(0充值,1购买祭品)
     */
    @ApiModelProperty("类型(0充值,1购买祭品)")
    private Integer type;
    /**
     * 祭品id
     */
    @ApiModelProperty("祭品id")
    private Long sacrificeId;
    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Integer num;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String mark;
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
