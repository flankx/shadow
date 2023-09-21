package com.github.shadow.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {

    @Bean(name = "captchaProducer")
    public DefaultKaptcha getKaptchaBean() {
        // 实例一个DefaultKaptcha
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        // 创建配置对象
        Properties properties = new Properties();
        // 设置边框
        properties.setProperty("kaptcha.border", "yes");
        // 设置颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 设置字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 设置宽度
        properties.setProperty("kaptcha.image.width", "125");
        // 高度
        properties.setProperty("kaptcha.image.height", "50");
        // 设置session.key
        properties.setProperty("kaptcha.session.key", "code");
        // 设置文本长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 设置字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
