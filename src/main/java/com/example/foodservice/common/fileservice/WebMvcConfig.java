package com.example.foodservice.common.fileservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Создаем объект File, чтобы Java сама построила правильный абсолютный путь
        File uploadDir = new File("uploads");
        String absolutePath = uploadDir.getAbsolutePath();

        // Превращаем в правильный URL для файловой системы (всегда заканчивается на /)
        String resourceLocation = "file:" + absolutePath + File.separator;

        registry.addResourceHandler("/static/**")
                .addResourceLocations(resourceLocation);
    }
}
