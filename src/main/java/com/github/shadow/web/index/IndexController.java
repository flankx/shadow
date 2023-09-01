package com.github.shadow.web.index;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.shadow.pojo.LoginDTO;
import com.github.shadow.pojo.R;
import com.github.shadow.util.ShiroUtils;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@ApiIgnore
@Controller
public class IndexController {

    @GetMapping(value = "/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping(value = "/index")
    public String home(ModelMap modelMap) {
        modelMap.put("user", ShiroUtils.getCurrentUser());
        return "/index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "/login";
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录", notes = "传入登录信息")
    @ResponseBody
    public R<String> login(HttpServletRequest request, @RequestBody LoginDTO login) {
        String username = login.getUsername();
        String password = login.getPassword();
        String captcha = login.getCaptcha();
        String rememberMe = login.getRemember();
        Subject subject = SecurityUtils.getSubject();
        // 比较验证码
        if (!request.getSession().getAttribute("kaptcha").equals(captcha)) {
            return R.error(400, "验证码错误！");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            log.info("sessionID : {}", subject.getSession().getId());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return R.error(400, "用户名或密碼錯誤！");
        }
        return R.success("登录成功！");
    }

    @GetMapping(value = "/logout")
    public String logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(ModelMap modelMap) {
        modelMap.put("user", ShiroUtils.getCurrentUser());
        return "/profile";
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
