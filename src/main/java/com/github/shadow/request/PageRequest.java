package com.github.shadow.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class PageRequest {
    @ApiModelProperty("当前页")
    private int current = 1;
    @ApiModelProperty("每页的数量")
    private int size = 10;
    @ApiModelProperty(hidden = true)
    private String ascs;
    @ApiModelProperty(hidden = true)
    private String descs;
}
