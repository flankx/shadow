package com.denachina.shadow.controller;


import com.denachina.shadow.dao.SysUser;
import com.denachina.shadow.pojo.LoginForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("cLoginForm", loginForm);
        model.addAttribute("cMessage", "");
        return "login/index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/login/submit", method = RequestMethod.POST)
    public String loginSubmit(HttpServletRequest request, @Valid @ModelAttribute("cLoginForm") LoginForm loginForm,BindingResult bindingResult
                              ,Model model) {
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
            logger.info("sessionID : {}", String.valueOf(currentUser.getSession().getId()));
        } catch (AuthenticationException e) {
            model.addAttribute("pwd_message", "用户名或密碼錯誤");
            e.printStackTrace();
            return "login/index";
        }

        return "home/index";
    }
}
