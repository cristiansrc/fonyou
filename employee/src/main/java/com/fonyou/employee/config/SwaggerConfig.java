/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fonyou.employee.config;

/**
 *
 * @author Cristhiam Reina
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import springfox.documentation.builders.PathSelectors;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket studentsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(tareaAPIInfo()).select()
            .apis(RequestHandlerSelectors.basePackage("com.fonyou.employee.rest"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo tareaAPIInfo() {
        return new ApiInfoBuilder().title("Employee API")
            .description("Employee API ")
            .version("1.0").build();
    }

}
