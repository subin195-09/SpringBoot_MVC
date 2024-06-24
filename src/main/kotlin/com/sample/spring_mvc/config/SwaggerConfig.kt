package com.sample.spring_mvc.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(servers = [Server(url = "/", description = "Server URL")])
class SwaggerConfig {
  @Bean
  fun CustomOpenAPI(): OpenAPI {
    return OpenAPI()
      .info(
        Info().title("Spring MVC Sample API")
          .version("1.0.0")
      )
  }
}