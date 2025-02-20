package com.iih.healthcare.uploader.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zuhair Ahmaed
 * @version 1.0
 * @created 15-06-2022
 */
@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${spring-doc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Uploader Service Module API")
                        .version(appVersion)
                        .description("This is the API documentation for Uploader Service module")
                        .termsOfService("http://iihsolutions.com/terms/")
                        .license(new License().name("Infirma 1.0")
                                .url("http://iihsolutions.com/")));
    }

}
