package com.flab.artshare.naverCloud

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "cloud.naver")
data class NaverCloudConfig(
    val accessKey: String = "dummy_key",
    val secretKey: String = "dummy_key",
    val region: String = "dummy_region",
    val endpoint: String = "dummy_end_point",
    val bucket: String = "dummy_bucket",
)
