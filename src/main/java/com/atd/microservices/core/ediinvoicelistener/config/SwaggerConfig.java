package com.atd.microservices.core.ediinvoicelistener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@EnableSwagger2WebFlux
@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${env.host.url:#{null}}")
    private String envHostURL;

    @Bean
    public Docket publishedAPI() {
        if(envHostURL != null) {
            return (new Docket(DocumentationType.SWAGGER_2)).host(envHostURL.concat("/").concat(appName)).groupName("EDI Invoice Listener Service")
                    .apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.atd.domain.microservices.ediinvoicelistener.controller")).paths(PathSelectors.ant("/**")).build();
        } else {
            return (new Docket(DocumentationType.SWAGGER_2)).groupName("EDI Invoice Listener Service")
                    .apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.atd.domain.microservices.ediinvoicelistener.controller")).paths(PathSelectors.ant("/**")).build();
        }
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title("EDI Invoice Listener Service")
                .description("REST API")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("1.0").build();
    }
}
