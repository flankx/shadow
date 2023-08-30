package com.github.shadow.web.index;

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
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;

@Controller
public class KcaptchaController {

    @Autowired
    @Qualifier(value = "captchaProducer")
    private Producer captchaProducer;

    @GetMapping(value = "/kaptcha")
    public ModelAndView kaptcha(HttpServletRequest request, HttpServletResponse response) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
