package com.flab.artshare.application

import com.flab.artshare.StorageService
import com.flab.artshare.dto.CreateProfileReq
import com.flab.artshare.dto.CreateProfileRes
import com.flab.artshare.profile.ProfileService
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class CreateProfileAndUploadImgService(
    private val profileService: ProfileService,
    private val storageService: StorageService,
) {
    fun execute(uid: String, req: CreateProfileReq): CreateProfileRes {
        val imgPath = runCatching { this.storageService.uploadImage(req.profileImg!!) }
            .getOrElse { throw IOException("Error uploading image to Storage", it) }

        val profile = runCatching { this.profileService.createProfile(req.toEntity(uid, imgPath!!)) }
            .getOrElse {
                when (it) {
                    is IllegalStateException -> throw IllegalStateException("Error message", it)
                    is IllegalArgumentException -> throw IllegalArgumentException("Error message", it)
                    else -> throw UnsupportedOperationException(it)
                }
            }

        return CreateProfileRes(profile)
    }
}
