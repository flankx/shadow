package com.github.shadow.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.github.shadow.pojo.LoginForm;

@Api(value = "用户登录", tags = "用户登录接口")
@Controller
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping(value = "/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("cLoginForm", loginForm);
        model.addAttribute("cMessage", "");
        return "login/index";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/login/submit")
    public String loginSubmit(HttpServletRequest request, @Valid @ModelAttribute("cLoginForm") LoginForm loginForm,
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("####[1] login form error: {}", bindingResult.getAllErrors());
            return "login/index";
        }

        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            currentUser.login(token);
            logger.info("sessionID : {}", currentUser.getSession().getId());
        } catch (AuthenticationException e) {
            model.addAttribute("pwd_message", "用户名或密碼錯誤");
            e.printStackTrace();
            return "login/index";
        }

        return "home/index";
    }
}
