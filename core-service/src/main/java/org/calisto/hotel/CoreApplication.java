package org.calisto.hotel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "REST API Documentation",
                description = "Description of endpoints for the REST API",
                version = "1.0"
        )
)
@PropertySources({
        @PropertySource("classpath:application-local.properties"),
        @PropertySource("classpath:application-docker.properties"),
})
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
