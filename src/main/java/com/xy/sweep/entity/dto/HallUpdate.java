package com.xy.sweep.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author climb.xu
 * @date 2022/2/9 15:17
 */
@Data
@ApiModel("修改纪念馆信息")
public class HallUpdate {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("纪念馆名称")
    private String title;

    @ApiModelProperty("纪念馆背景")
    private String background;

    @ApiModelProperty("纪念馆类型(0私人馆,1名人馆,2恩师馆)")
    private Integer type;
}
