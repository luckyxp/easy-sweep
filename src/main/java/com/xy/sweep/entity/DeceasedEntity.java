package com.xy.sweep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 逝者表
 *
 * @author climb.xu
 * @email 2271613696@qq.com
 * @date 2021-11-30 12:20:50
 */
@Data
@TableName("deceased")
@ApiModel("逝者表")
public class DeceasedEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 逝者姓名
     */
    @ApiModelProperty("逝者姓名")
    private String name;
    /**
     * 逝者别名
     */
    @ApiModelProperty("逝者别名")
    private String alias;
    /**
     * 逝者性别(0女 1男 2其他)
     */
    @ApiModelProperty("逝者性别(0女 1男 2其他)")
    private Integer gender;
    /**
     * 逝者照片
     */
    @ApiModelProperty("逝者照片")
    private String picture;
    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private Date birthday;
    /**
     * 逝世日期
     */
    @ApiModelProperty("逝世日期")
    private Date deathday;
    /**
     * 纪念馆编号
     */
    @ApiModelProperty("纪念馆编号")
    private Long hallId;
    /**
     * 生肖(1-12)
     */
    @ApiModelProperty("生肖(1-12)")
    private Integer zodiac;
    /**
     * 宗教
     */
    @ApiModelProperty("宗教")
    private String religion;
    /**
     * 籍贯
     */
    @ApiModelProperty("籍贯")
    private String nat;
    /**
     * 生活地址
     */
    @ApiModelProperty("生活地址")
    private String habitation;
    /**
     * 墓地地址
     */
    @ApiModelProperty("墓地地址")
    private String cemetery;
    /**
     * 生平简介
     */
    @ApiModelProperty("生平简介")
    private Long introduction;
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
