package com.ai.spring_ai;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class GenAIController {
    private final ChatService chatService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    public GenAIController(ChatService chatService, ImageService imageService, RecipeService recipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }


    @GetMapping("query-open-ai")
    public String query(@RequestParam String prompt){
        return chatService.query(prompt);
    }
    @GetMapping("query-open-ai-options")
    public String queryWithOptions(@RequestParam String prompt){
        return chatService.queryWithOptions(prompt);
    }

    @GetMapping("generate-image")
    public void generateImage(HttpServletResponse response, @RequestParam String prompt) throws IOException {
        ImageResponse imageResponse = imageService.generateImage(prompt);
        response.sendRedirect(imageResponse.getResult().getOutput().getUrl());
    }

    @GetMapping("generate-images-with-params")
    public List<String> generateImagesWithParams(
            @RequestParam String prompt,
            @RequestParam(defaultValue = "hd") String quality,
            @RequestParam(defaultValue = "1") int n,
            @RequestParam(defaultValue = "1024") int height,
            @RequestParam(defaultValue = "1024") int weight) {
        ImageResponse imageResponse = imageService.generateImagesWithParams(prompt, quality, n, height, weight);
        return imageResponse.getResults()
                .stream()
                .map(result -> result.getOutput().getUrl())
                .toList();
    }

    @GetMapping("generate-recipe")
    public String generateRecipe(@RequestParam String ingredients,
                          @RequestParam(defaultValue = "any") String cuisine,
                          @RequestParam(defaultValue = "none") String dietaryRestriction){
        ChatResponse chatResponse = recipeService.generateRecipe(ingredients, cuisine, dietaryRestriction);
        return chatResponse.getResult().getOutput().getContent();
    }
}
