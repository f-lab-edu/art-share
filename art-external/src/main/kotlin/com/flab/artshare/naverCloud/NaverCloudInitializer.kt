package com.flab.artshare.naverCloud

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NaverCloudInitializer(private val config: NaverCloudConfig) {
    @Bean
    fun naverCloud(): NaverCloudApi {
        return NaverCloudApi(
            config.bucket,
            config.accessKey,
            config.secretKey,
            config.region,
            config.endpoint)
    }
}
