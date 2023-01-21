package com.tutrit.quickstart.restfull.servise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        RequestParameter customHeader = new RequestParameterBuilder()
                .name("X-TelegramChatId").in(ParameterType.HEADER)
                .required(false)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .globalRequestParameters(List.of(customHeader))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
