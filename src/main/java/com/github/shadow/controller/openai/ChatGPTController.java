package com.github.shadow.controller.openai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.github.shadow.listener.GPTEventSourceListener;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatGPTController {

    private final static String API_HOST = "https://api.openai.com/";
    private final static String API_KEY = "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";

    // 实际请用数据库管理上下文
    private static Map<String, List<Message>> context = new HashMap<>();

    @GetMapping("/sse")
    @CrossOrigin
    public SseEmitter sseEmitter(String key, String id, String prompt) {
        ChatGPTStream chatGPTStream =
            ChatGPTStream.builder().timeout(50).apiKey(StringUtils.isBlank(key) ? API_KEY : key)
                // .proxy(proxy)
                .apiHost(API_HOST).build().init();

        SseEmitter sseEmitter = new SseEmitter(-1L);

        GPTEventSourceListener listener = new GPTEventSourceListener(sseEmitter);
        Message message = Message.of(prompt);

        List<Message> messages = context.computeIfAbsent(id, k -> new ArrayList<>());
        messages.add(message);
        listener.setOnComplate(msg -> {
            add(id, message);
            add(id, Message.ofAssistant(msg));
        });
        chatGPTStream.streamChatCompletion(messages, listener);

        return sseEmitter;
    }

    public void add(String id, Message message) {
        List<Message> messages = context.computeIfAbsent(id, k -> new ArrayList<>());
        messages.add(message);
    }

}
