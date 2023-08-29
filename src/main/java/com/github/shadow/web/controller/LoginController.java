package com.github.shadow.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.github.shadow.pojo.LoginForm;
import com.github.shadow.web.response.Result;

import io.swagger.annotations.Api;

@Api(value = "用户登录", tags = "用户登录接口")
@RestController
@RequestMapping("/api")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping(value = "/login")
    @ResponseBody
    public Result<String> loginSubmit(@RequestBody LoginForm loginForm) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            logger.info("sessionID : {}", currentUser.getSession().getId());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.error(400, "用户名或密碼錯誤！");
        }
        return Result.success("登录成功！");
    }
}
