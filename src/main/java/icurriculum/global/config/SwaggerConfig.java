package icurriculum.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        // Define the security scheme


        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("i-curriculum Spring Swagger")
                .description("i-curriculum Swagger Doc입니다.")
                .version("0.0.1");

    }

}
