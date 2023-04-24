package com.flab.artshare.config.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun publicApi() = GroupedOpenApi.builder()
        .group("v1-definition")
        .pathsToMatch("/api/**")
        .build()

    @Bean
    fun artShareOpenAPI() =
        OpenAPI()
            .info(
                Info().title("Art Share API")
                    .description("Art Share API 명세서입니다.")
                    .version("v1"),
            )
}
