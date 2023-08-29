package com.github.shadow.pojo;

import lombok.Data;

@Data
public class LoginDTO {

    private String username;

    private String password;

    private String captcha;

    private String remember;
}
