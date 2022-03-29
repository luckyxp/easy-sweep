package com.xy.sweep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 评论表
 *
 * @author climb.xu
 * @date 2022/2/22 19:09
 */
@Data
@TableName("comment")
@ApiModel("评论表")
public class CommentEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId
    @ApiModelProperty("评论id")
    private Long commentId;
    /**
     * 文章id
     */
    @ApiModelProperty("文章id")
    private Long contentId;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 父评论id
     */
    @ApiModelProperty("父评论id")
    private Long parentId;
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
