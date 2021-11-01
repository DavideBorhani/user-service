package com.davideborhani.userservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/user/*"))
                .apis(RequestHandlerSelectors.basePackage("com.davideborhani.userservice"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "User service API",
                "A spring-boot service that insert, retrieve, update and delete a user(only adult and resident in France)",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact(
                        "Davide Borhani",
                        "https://mc.linkedin.com/in/davide-borhani-5741aa18a",
                        "davideborhani@live.it"),
                "API License",
                "https://mc.linkedin.com/in/davide-borhani-5741aa18a",
                Collections.emptyList()
        );
    }
}