package com.example.friendservice.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${ImgLocation}")
    private String imageLocation;

    @Value("${ProfileImgLocation}")
    private String profileImageLocation;

    private final List<String> allowedOrigins;
    private final Environment environment;

    @Autowired
    public WebMvcConfig(Environment environment) {
        this.environment = environment;
        String[] origins = environment.getProperty("cors.allowed-origins", String[].class);
        if (origins != null) {
            allowedOrigins = List.of(origins);
        } else {
            allowedOrigins = List.of(); // 기본값으로 빈 리스트
        }
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        Object[] objs = allowedOrigins.toArray();
        String[] origins = Arrays.copyOf(objs, objs.length, String[].class);
        System.out.println(origins);
        registry.addMapping("/**") // 모든 경로에 대해 적용
                .allowedOrigins(
                        origins
                ) // 허용할 Origin (와일드카드 사용 불가)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 자격 증명 허용 (쿠키 등)
                .maxAge(3600); // Preflight 요청 캐싱 시간 (1시간)
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageLocation + "/");

        registry.addResourceHandler("/profileImages/**")
                .addResourceLocations("file:" + profileImageLocation + "/");
    }


}

