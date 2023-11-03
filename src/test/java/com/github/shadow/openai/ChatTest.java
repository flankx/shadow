package com.github.shadow.openai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.*;
import com.plexpt.chatgpt.listener.ConsoleStreamListener;

public class ChatTest {

    private final ChatGPT chatGPT =
        ChatGPT.builder().timeout(600).apiKey("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")
            // .proxy(proxy)
            .apiHost("https://api.openai.com/").build().init();

    private final ChatGPTStream chatGPTStream =
        ChatGPTStream.builder().timeout(600).apiKey("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")
            // .proxy(proxy)
            .apiHost("https://api.openai.com/").build().init();

    @Test
    public void test1() {

        String res = chatGPT.chat("写一段七言绝句诗，题目是：火锅！");
        System.out.println(res);
    }

    @Test
    public void test2() {

        Message system = Message.ofSystem("你现在是一个诗人，专门写七言绝句");
        Message message = Message.of("写一段七言绝句诗，题目是：火锅！");

        ChatCompletion chatCompletion = ChatCompletion.builder().model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
            .messages(Arrays.asList(system, message)).maxTokens(3000).temperature(0.9).build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        System.out.println(res);

    }

    @Test
    public void test3() {

        List<ChatFunction> functions = new ArrayList<>();
        ChatFunction function = new ChatFunction();
        function.setName("getCurrentWeather");
        function.setDescription("获取给定位置的当前天气");
        function.setParameters(ChatFunction.ChatParameter.builder().type("object").required(Arrays.asList("location"))
            .properties(JSON.parseObject("{\n" + "          \"location\": {\n" + "            \"type\": \"string\",\n"
                + "            \"description\": \"The city and state, e.g. San Francisco, " + "CA\"\n"
                + "          },\n" + "          \"unit\": {\n" + "            \"type\": \"string\",\n"
                + "            \"enum\": [\"celsius\", \"fahrenheit\"]\n" + "          }\n" + "        }"))
            .build());
        functions.add(function);

        Message message = Message.of("上海的天气怎么样？");
        ChatCompletion chatCompletion =
            ChatCompletion.builder().model(ChatCompletion.Model.GPT_3_5_TURBO_0613.getName())
                .messages(Arrays.asList(message)).functions(functions).maxTokens(8000).temperature(0.9).build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        ChatChoice choice = response.getChoices().get(0);
        Message res = choice.getMessage();
        System.out.println(res);
        if ("function_call".equals(choice.getFinishReason())) {
            FunctionCallResult functionCall = res.getFunctionCall();
            String functionCallName = functionCall.getName();

            if ("getCurrentWeather".equals(functionCallName)) {
                String arguments = functionCall.getArguments();
                JSONObject jsonObject = JSON.parseObject(arguments);
                String location = jsonObject.getString("location");
                String unit = jsonObject.getString("unit");
                String weather = "{ \"temperature\": 22, \"unit\": \"celsius\", \"description\": \"晴朗\" }";

                callWithWeather(weather, res, functions);
            }
        }

    }

    @Test
    public void test4() {

        ConsoleStreamListener listener = new ConsoleStreamListener();
        Message message = Message.of("写一段七言绝句诗，题目是：火锅！");
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        chatGPTStream.streamChatCompletion(chatCompletion, listener);

    }

    private void callWithWeather(String weather, Message res, List<ChatFunction> functions) {

        Message message = Message.of("上海的天气怎么样？");
        Message function1 = Message.ofFunction(weather);
        function1.setName("getCurrentWeather");
        ChatCompletion chatCompletion = ChatCompletion.builder()
            .model(ChatCompletion.Model.GPT_3_5_TURBO_0613.getName()).messages(Arrays.asList(message, res, function1))
            .functions(functions).maxTokens(8000).temperature(0.9).build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        ChatChoice choice = response.getChoices().get(0);
        Message res2 = choice.getMessage();
        // 上海目前天气晴朗，气温为 22 摄氏度。
        System.out.println(res2.getContent());

    }

}
