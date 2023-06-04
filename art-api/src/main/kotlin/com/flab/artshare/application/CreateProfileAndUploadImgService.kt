package com.flab.artshare.application

import com.flab.artshare.StorageService
import com.flab.artshare.dto.CreateProfileReq
import com.flab.artshare.dto.CreateProfileRes
import com.flab.artshare.profile.ProfileService
import org.springframework.stereotype.Service

@Service
class CreateProfileAndUploadImgService(
    private val profileService: ProfileService,
    private val storageService: StorageService,
) {
    fun execute(uid: String, req: CreateProfileReq): CreateProfileRes {
        val imgPath = this.storageService.uploadImage(req.profileImg!!)

        val profile = runCatching { this.profileService.createProfile(req.toEntity(uid, imgPath!!)) }
            .getOrElse {
                when (it) {
                    is IllegalStateException -> throw IllegalStateException("Error message", it)
                    else -> throw IllegalArgumentException("Error message", it)
                }
            }

        return CreateProfileRes(profile)
    }
}
