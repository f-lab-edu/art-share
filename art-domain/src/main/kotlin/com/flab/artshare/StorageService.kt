package com.flab.artshare

import com.flab.artshare.naverCloud.NaverCloudApi
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

@Service
class StorageService(private val storage: NaverCloudApi) {

    fun uploadImage(multipartFile: MultipartFile): String? {
        val imgName: String = makeRandomName()

        uploadFile(imgName, multipartFile)

        return runCatching { storage.getUrl(imgName) }.getOrNull()
    }

    private fun makeRandomName(): String = UUID.randomUUID().toString() + ".jpg"

    private fun uploadFile(fileName: String, file: MultipartFile) {
        val convertFile = File(fileName)

        FileOutputStream(convertFile).use { fos -> fos.write(file.bytes) }

        runCatching { storage.uploadFile(fileName, convertFile) }
            .onSuccess { convertFile.delete() }
            .onFailure {
                convertFile.delete()
                throw IOException("Failed to upload file: $fileName", it)
            }
    }
}
