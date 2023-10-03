package org.calisto.hotel.config.swagger;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Custom API Documentation")
                .description("Custom API operations documented separately")
                .version("1.0")
                .build();
    }
}
