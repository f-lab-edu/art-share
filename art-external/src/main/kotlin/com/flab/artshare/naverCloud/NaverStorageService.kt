package com.flab.artshare.naverCloud

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@Service
class NaverStorageService(private val storage: AmazonS3) {

    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String? = null
    private val logger = LoggerFactory.getLogger(NaverStorageService::class.java)

    fun uploadImage(multipartFile: MultipartFile): String {
        val imgName: String = makeRandomName()

        uploadFile(imgName, multipartFile)

        return getUrl(imgName)
    }

    private fun makeRandomName(): String = UUID.randomUUID().toString() + ".jpg"

    private fun uploadFile(fileName: String, file: MultipartFile) {
        val convertFile = File(fileName)

        FileOutputStream(convertFile).use { fos -> fos.write(file.bytes) }

        runCatching { uploadFileWithCloud(fileName, convertFile) }
            .onFailure { ex -> logger.error(ex.message) }
            .also { convertFile.delete() }
    }
    private fun uploadFileWithCloud(fileName: String, file: File) = storage.putObject(
        PutObjectRequest(bucket, fileName, file)
            .withCannedAcl(CannedAccessControlList.PublicRead),
    )
    private fun getUrl(fileName: String): String = storage.getUrl(bucket, fileName).toString()
}
