package com.xy.sweep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 纪念馆表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
@Data
@TableName("hall")
@ApiModel("纪念馆表")
public class HallEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 纪念馆编号
     */
    @ApiModelProperty("纪念馆编号")
    private String identifier;
    /**
     * 逝者id
     */
    @ApiModelProperty("逝者id")
    private Long deceasedId;
    /**
     * 纪念馆名称
     */
    @ApiModelProperty("纪念馆名称")
    private String title;
    /**
     * 纪念馆背景
     */
    @ApiModelProperty("纪念馆背景")
    private String background;
    /**
     * 纪念馆类型(0私人馆,1名人馆,2恩师馆)
     */
    @ApiModelProperty("纪念馆类型(0私人馆,1名人馆,2恩师馆)")
    private Integer type;
    /**
     * 深情指数
     */
    @ApiModelProperty("深情指数")
    private Long deepNum;
    /**
     * 爱心指数
     */
    @ApiModelProperty("爱心指数")
    private Long kindNum;
    /**
     * 人气指数
     */
    @ApiModelProperty("人气指数")
    private Long visitorNum;
    /**
     * 纪念馆状态(0审核中,1已通过,2不通过)
     */
    @ApiModelProperty("纪念馆状态(0审核中,1已通过,2不通过)")
    private Integer status;
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
