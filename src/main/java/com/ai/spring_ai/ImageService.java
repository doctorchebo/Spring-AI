package com.ai.spring_ai;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private OpenAiImageModel openAiImageModel;
    public ImageResponse generateImage(String prompt){
        return openAiImageModel.call(
                new ImagePrompt(
                    prompt,
                    OpenAiImageOptions.builder()
                            .quality("hd")
                            .model("dall-e-3")
                            .style("vivid")
                            .N(1)
                            .height(1024)
                            .width(1024).build()
                )
        );
    }

    public ImageResponse generateImagesWithParams(String prompt, String quality, int n, int height, int weight) {
        return openAiImageModel.call(
                new ImagePrompt(
                        prompt,
                        OpenAiImageOptions.builder()
                                .quality(quality)
                                .model("dall-e-2")
                                .style("vivid")
                                .N(n)
                                .height(height)
                                .width(weight).build()
                )
        );
    }

}
