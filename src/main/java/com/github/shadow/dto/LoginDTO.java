package com.github.shadow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "登录信息")
public class LoginDTO {
    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;
    @ApiModelProperty(value = "密码", example = "xxxx")
    private String password;
    @ApiModelProperty(value = "验证码", example = "abcd")
    private String captcha;
    @ApiModelProperty(value = "记住我", example = "on | off")
    private String remember;
}
