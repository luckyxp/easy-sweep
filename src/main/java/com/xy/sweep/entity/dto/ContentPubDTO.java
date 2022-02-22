package com.xy.sweep.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author climb.xu
 * @date 2022/2/22 16:56
 */
@Data
@ApiModel("发布文章 逝者id|纪念馆id任意一个")
public class ContentPubDTO {
    private static final long serialVersionUID = 1L;
    /**
     * 逝者id
     */
    @ApiModelProperty("逝者id")
    private Long deceasedId;
    /**
     * 纪念馆id
     */
    @ApiModelProperty("纪念馆id")
    private Long hallId;
    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;
    /**
     * 分类(0时空信箱,1追忆文章,2一生大事,3生平简介)
     */
    @ApiModelProperty("分类(0时空信箱,1追忆文章,2一生大事,3生平简介)")
    private Integer type;

}
