package com.xy.sweep.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author climb.xu
 * @date 2022/2/9 15:06
 */
@Data
@ApiModel("建馆审核")
public class HallAudit {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("纪念馆状态(1已通过,2不通过)")
    private Integer status;
}
