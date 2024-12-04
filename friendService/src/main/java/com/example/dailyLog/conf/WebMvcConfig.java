package com.example.dailyLog.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${ImgLocation}")
    private String imageLocation;

    @Value("${ProfileImgLocation}")
    private String profileImageLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageLocation + "/");

        registry.addResourceHandler("/profileImages/**")
                .addResourceLocations("file:" + profileImageLocation + "/");
    }


}

