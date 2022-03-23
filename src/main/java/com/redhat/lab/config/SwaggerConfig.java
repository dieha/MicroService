package com.redhat.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /* http://localhost:8034/v2/api-docs
     * http://localhost:8034/swagger-ui.html
     * 
     * 
     * Docket bean in a Spring Boot configuration to configure Swagger 2 for the
     * application. A Springfox Docket instance provides the primary API
     * configuration with sensible defaults and convenience methods for
     * configuration
     */
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.redhat.lab"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact("LocalLab", "https://redhat.com", "");
        return new ApiInfoBuilder()
                .title("Spring Boot Restful Webservice Demo")
                .description("Spring Boot Restful Webservice Demo")
                .version("0.0.1")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build();
    }

}