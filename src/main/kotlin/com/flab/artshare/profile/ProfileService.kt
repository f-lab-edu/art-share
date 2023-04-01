package com.flab.artshare.profile

import com.flab.artshare.common.dto.CreateProfileReq
import com.flab.artshare.common.dto.CreateProfileRes
import com.flab.artshare.common.dto.ReadProfileRes
import com.flab.artshare.storage.StorageService
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class ProfileService(
    private val storageService: StorageService,
    private val profileRepo: ProfileRepository
) {
    fun createProfile(uid: String, req: CreateProfileReq): CreateProfileRes {
        validateProfileRequest(req)

        checkProfileExists(uid)

        val imgPath = storageService.uploadImage(req.profileImg)

        val profile = profileRepo.save(req.toEntity(uid, imgPath))

        return CreateProfileRes(profile)
    }

    private fun checkProfileExists(uid: String) {
        if (profileRepo.existsByUid(uid))
            throw IllegalStateException("Profile with the provided uid already exists.")
    }

    private fun validateProfileRequest(req: CreateProfileReq) {
        if (profileRepo.existsByDisplayNameOrAbout(req.displayName, req.about))
            throw IllegalArgumentException("Profile with the provided display name or about already exists.")
    }

    fun findProfile(uid: String): ReadProfileRes{
        val profile = profileRepo.findByUid(uid)
            .orElseThrow { NullPointerException("Profile with the provided uid is not exists") }

        return ReadProfileRes(profile)
    }
}
