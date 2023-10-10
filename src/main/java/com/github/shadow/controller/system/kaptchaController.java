package com.github.shadow.controller.system;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Producer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/kaptcha")
@Api(value = "验证码", tags = "验证码接口")
public class kaptchaController {

    @Autowired
    @Qualifier(value = "captchaProducer")
    private Producer captchaProducer;

    @GetMapping
    @ApiOperation(value = "获取字符验证码")
    public void kaptcha(HttpServletRequest request, HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            String code = captchaProducer.createText();
            BufferedImage image = captchaProducer.createImage(code);
            request.getSession().setAttribute("kaptcha", code);
            ImageIO.write(image, "jpg", outputStream);
            log.info("生成验证码成功！code = {}", code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
