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

        // Печатаем в консоль, чтобы ты СВОИМИ ГЛАЗАМИ увидел, где Spring ищет файлы
        System.out.println("============== STATIC STORAGE PATH ==============");
        System.out.println("Spring expects files to be here: " + absolutePath);
        System.out.println("=================================================");

        // Превращаем в правильный URL для файловой системы (всегда заканчивается на /)
        String resourceLocation = "file:" + absolutePath + File.separator;

        registry.addResourceHandler("/static/**")
                .addResourceLocations(resourceLocation);
    }
}
