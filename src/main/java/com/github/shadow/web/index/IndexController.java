package com.github.shadow.web.index;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class IndexController {

    @GetMapping(value = "/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping(value = "/index")
    public String home() {
        return "/index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "/login";
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

    @GetMapping(value = "/swagger")
    public String swagger() {
        return "redirect:/doc.html";
    }

    @GetMapping(value = "/druid")
    public String druid() {
        return "redirect:/druid/index.html";
    }

}
