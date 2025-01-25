package com.ai.spring_ai;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {
    private final ChatModel chatModel;

    public RecipeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }
    public ChatResponse generateRecipe(String ingredients, String cuisine, String dietaryRestriction){
        var template = """
                Please generate a recipe with the following ingredients: {ingredients}.
                The recipe should follow this type of cuisine: {cuisine}.
                The recipe should follow this dietary restriction: {dietaryRestriction}.
                The response should contain a title, list of ingredients and steps to prepare the recipe.
                """;
        Map<String, Object> params = Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "dietaryRestriction", dietaryRestriction
        );

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(params);
//        OpenAiChatOptions options = OpenAiChatOptions.builder()
//                .model(OpenAiApi.ChatModel.GPT_4_O_MINI.getValue())
//                .temperature(0.8)
//                .build();
        return chatModel.call(prompt);
    }
}
