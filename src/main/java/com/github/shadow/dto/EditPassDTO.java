package com.github.shadow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "修改密码")
public class EditPassDTO {
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;
    @ApiModelProperty(value = "新密码")
    private String password;
    @ApiModelProperty(value = "确认新密码")
    private String confirmPassword;
}
