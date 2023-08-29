package com.github.shadow.request;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "计算请求")
public class EvaluateRequest {
    @ApiModelProperty(value = "参与计算的键值对", example = "{'a': 1, 'b': 2, 'c': 3}")
    private Map<String, Object> source;
    @ApiModelProperty(value = "参与计算的表达式", example = "a + b - c")
    private String expression;
}
