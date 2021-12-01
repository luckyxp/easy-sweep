package com.xy.sweep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 祭品表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
@Data
@TableName("sacrifice")
@ApiModel("祭品表")
public class SacrificeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 祭品名
     */
    @ApiModelProperty("祭品名")
    private String name;
    /**
     * 祭品图片
     */
    @ApiModelProperty("祭品图片")
    private String img;
    /**
     * 祭品类型
     */
    @ApiModelProperty("祭品类型")
    private Integer type;
    /**
     * 祭品价格
     */
    @ApiModelProperty("祭品价格")
    private Long price;
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
