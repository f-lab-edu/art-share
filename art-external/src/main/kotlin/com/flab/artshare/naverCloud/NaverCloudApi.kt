package com.flab.artshare.naverCloud

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import java.io.File
import java.io.IOException

class NaverCloudApi(
    private val bucket: String,
    private val amazonS3Client: AmazonS3,
) {
    fun uploadFile(fileName: String, file: File) = runCatching {
        this.amazonS3Client.putObject(
            PutObjectRequest(bucket, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead),
        )
    }.onFailure { throw IOException("Failed to upload file: $fileName", it) }

    fun getUrl(fileName: String): String =
        runCatching {
            amazonS3Client.getUrl(bucket, fileName).toString()
        }.getOrElse { throw IOException("Failed to get img url: $fileName", it) }
}
