package com.denachina.shadow.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.text.MessageFormat;
import java.time.Instant;

/**
 * Spring websocket demo
 */
@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(JSONObject message) {
        return MessageFormat.format("Hello, {0}! Welcome back to shadow ! time {1}", HtmlUtils.htmlEscape(message.getString("name")), HtmlUtils.htmlEscape(String.valueOf(Instant.now())));
    }
}