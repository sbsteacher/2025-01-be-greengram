package com.green.greengram.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration //빈등록
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final String uploadPath;

    public WebMvcConfiguration(@Value("${constants.file.upload-directory}") String uploadPath) {
        this.uploadPath = uploadPath;
        log.info("Upload Path: {}", uploadPath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**")
                .addResourceLocations("file:" + uploadPath);
    }

}
