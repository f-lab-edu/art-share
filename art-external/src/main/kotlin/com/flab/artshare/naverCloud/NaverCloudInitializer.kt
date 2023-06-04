package com.flab.artshare.naverCloud

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NaverCloudInitializer(private val config: NaverCloudConfig) {
    private val amazonS3Client: AmazonS3 =
        AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(config.accessKey, config.secretKey)))
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(config.endpoint, config.region))
            .build()

    @Bean
    fun naverCloud(): NaverCloudApi {
        return NaverCloudApi(
            config.bucket,
            this.amazonS3Client,
        )
    }
}
