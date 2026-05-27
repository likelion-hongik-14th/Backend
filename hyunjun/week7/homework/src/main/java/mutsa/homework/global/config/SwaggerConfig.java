package mutsa.homework.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.event.ComponentEvent;
import java.security.Security;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        io.swagger.v3.oas.models.info.Info info = new Info().title("멋쟁이사자처럼 14기")
                .description("유현준 백엔드 쇼핑몰 swagger").version("0.0.1");

        String securityScheme = "JWT TOKEN";
        io.swagger.v3.oas.models.security.SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securityScheme);

        Components components = new Components()
                .addSecuritySchemes(securityScheme, new io.swagger.v3.oas.models.security.SecurityScheme()
                        .name(securityScheme)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
