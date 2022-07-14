package com.aster.webchat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@MapperScan("com.aster.webchat.dao")
@EnableScheduling
@EnableOpenApi
public class WebChatWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebChatWebApplication.class, args);
    }
}
