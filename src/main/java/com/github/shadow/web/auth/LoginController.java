package com.github.shadow.web.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.shadow.pojo.LoginDTO;
import com.github.shadow.pojo.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户登录", tags = "用户登录接口")
@RestController
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录", notes = "传入登录信息")
    public R<String> login(@RequestBody LoginDTO loginForm) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            logger.info("sessionID : {}", currentUser.getSession().getId());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return R.error(400, "用户名或密碼錯誤！");
        }
        return R.success("登录成功！");
    }
}
