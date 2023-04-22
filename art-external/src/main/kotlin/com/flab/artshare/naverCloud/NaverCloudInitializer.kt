package com.flab.artshare.naverCloud

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException

@Configuration
class NaverCloudInitializer(private val config: NaverCloudConfig) {
    @Bean
    fun naverCloud(): NaverCloud {
        return NaverCloud(
            config.bucket,
            config.accessKey,
            config.secretKey,
            config.region,
            config.endpoint)
    }
}
