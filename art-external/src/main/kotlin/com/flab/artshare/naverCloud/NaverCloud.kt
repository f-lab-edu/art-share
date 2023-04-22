package com.flab.artshare.naverCloud

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import java.io.File

class NaverCloud(
    private val bucket: String,
    accessKey: String,
    secretKey: String,
    region: String,
    endpoint: String,
) {

    private val amazonS3Client: AmazonS3 =
        AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)))
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, region))
            .build()

    fun uploadFile(fileName: String, file: File): PutObjectResult = amazonS3Client.putObject(
        PutObjectRequest(bucket, fileName, file)
            .withCannedAcl(CannedAccessControlList.PublicRead)
    )

    fun getUrl(fileName: String): String = amazonS3Client.getUrl(bucket, fileName).toString()
}
