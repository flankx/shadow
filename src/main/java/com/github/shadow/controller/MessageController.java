package com.github.shadow.controller;

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * Spring websocket demo
 */
@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(Map<String, Object> message) {

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = dtf2.format(zonedDateTime);

        return MessageFormat.format("Hello, {0}! Welcome back to shadow! 登录时间: {1}",
            HtmlUtils.htmlEscape(String.valueOf(message.get("name"))), HtmlUtils.htmlEscape(dateTime));
    }
}