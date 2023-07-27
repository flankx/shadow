package com.github.shadow.web.request;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "计算请求")
public class EvaluateRequest {
    @ApiModelProperty(value = "参与计算的键值对", example = "{key:value}")
    private Map<String, Object> source;
    @ApiModelProperty(value = "参与计算的表达式", example = "a + b - c")
    private String expression;
}
