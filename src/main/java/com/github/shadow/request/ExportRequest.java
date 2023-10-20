package com.github.shadow.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xujianrong
 * @date 2023/10/16
 */
@Data
@ApiModel(description = "导出请求")
public class ExportRequest {
    @ApiModelProperty("导出类型")
    private String exportType;
    @ApiModelProperty(value = "报警发生开始时间；格式：YYYY-MM-DD HH:mm:ss", example = "2023-01-01 10:00:00")
    private String timeStart;
    @ApiModelProperty(value = "报警发生结束时间；格式：YYYY-MM-DD HH:mm:ss", example = "2023-01-02 10:00:00")
    private String timeEnd;
}
