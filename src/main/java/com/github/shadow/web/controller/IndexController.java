package com.github.shadow.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;

@Api(value = "页面控制器", tags = "页面控制接口")
@Controller
public class IndexController {
    @GetMapping(value = "/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "/home/index";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login/login";
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
}
