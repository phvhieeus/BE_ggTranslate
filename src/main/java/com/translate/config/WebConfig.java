package com.translate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Cho phép frontend gửi credentials (cookies)
        config.setAllowCredentials(true);

        // Cho phép frontend từ origin này gọi API
        // Trong môi trường phát triển
        config.addAllowedOrigin("http://localhost:5173");

        // Nếu bạn triển khai production, thêm domain thật của bạn vào đây
        // config.addAllowedOrigin("https://your-production-domain.com");

        // Cho phép tất cả các header
        config.addAllowedHeader("*");

        // Cho phép tất cả các phương thức HTTP
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}