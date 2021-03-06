package com.votingapp.configuration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Voting APP")
                        .version("1.0.0")
                        .description("Voting APP - API")
                        .termsOfService("https://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org"))
                        .contact(new Contact()
                                .name("Sadraque Nunes")
                                .url("https://www.linkedin.com/in/sadraquenunesmartiniano/")
                                .email("sadraquenunesmartiniano@gmail.com")));

    }
}