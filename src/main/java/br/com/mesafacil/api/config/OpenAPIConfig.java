package br.com.mesafacil.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${fasefood.openapi.dev-url}")
    private String devUrl;


    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Servidor de desenvolvimento");



        Contact contact = new Contact();
        contact.setName("Grupo 8 FIAP Pos-Tech");


        License licencaMIT = new License()
                .name("Licença MIT")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("API de Gerenciamento FaseFood")
                .version("1.0")
                .description("Esta API fornece endpoints para gerenciamento do sistema FaseFood, para o Tech Challenge PosTech FIAP")
                .contact(contact)
                .license(licencaMIT);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
