package com.denachina.shadow.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Spring websocket demo
 */
@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(JSONObject message) {

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String dateTime = dtf2.format(zonedDateTime);

        return MessageFormat.format(
                "Hello, {0}! Welcome back to shadow! 登录时间: {1}",
                HtmlUtils.htmlEscape(message.getString("name")),
                HtmlUtils.htmlEscape(dateTime)
        );
    }
}