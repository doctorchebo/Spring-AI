package com.ai.spring_ai;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    ChatModel chatModel;

    public String query(String prompt){
        return chatModel.call(prompt);
    }
    public String queryWithOptions(String prompt){
        ChatResponse response = chatModel.call(new Prompt(prompt, OpenAiChatOptions.builder()
                .model(OpenAiApi.ChatModel.GPT_4_O_MINI.getValue())
                .temperature(0.4)
                .build()));
        return response.getResult().getOutput().getContent();
    }
}
