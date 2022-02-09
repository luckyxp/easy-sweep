package com.xy.sweep.entity.dto;

import com.xy.sweep.entity.DeceasedEntity;
import com.xy.sweep.entity.HallEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author climb.xu
 * @date 2022/2/9 19:29
 */
@Data
@ApiModel("纪念馆搜索结果")
public class HallSearchDTO {
    @ApiModelProperty("纪念馆信息")
    private HallEntity hall;
    @ApiModelProperty("逝者信息")
    private DeceasedEntity deceased;
    @ApiModelProperty("建馆人")
    private String creator;
    @ApiModelProperty("建馆人id")
    private Long creatorId;
}
