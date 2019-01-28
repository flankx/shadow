package com.denachina.shadow.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginForm {

    @NotBlank(message = "用户名不能为空！")
    private String username;

    @Size(min=6, message = "密码位数错误！")
    private String password;

//    @Size(min=4, max=4, message="验证码错误！")
//    private String capture;

}
