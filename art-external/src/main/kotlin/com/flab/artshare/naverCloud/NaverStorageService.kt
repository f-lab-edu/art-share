package com.flab.artshare.naverCloud

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@Service
class NaverStorageService(private val storage: NaverCloud) {

    private val logger = LoggerFactory.getLogger(NaverStorageService::class.java)

    fun uploadImage(multipartFile: MultipartFile): String {

        val imgName: String = makeRandomName()

        uploadFile(imgName, multipartFile)

        return storage.getUrl(imgName)
    }

    private fun makeRandomName(): String = UUID.randomUUID().toString() + ".jpg"

    private fun uploadFile(fileName: String, file: MultipartFile) {
        val convertFile = File(fileName)

        FileOutputStream(convertFile).use { fos -> fos.write(file.bytes) }

        runCatching { storage.uploadFile(fileName, convertFile) }
            .onFailure { ex -> logger.error(ex.message) }
            .also { convertFile.delete() }
    }
}
