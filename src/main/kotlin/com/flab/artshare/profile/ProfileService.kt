package com.flab.artshare.profile

import com.flab.artshare.common.dto.*
import com.flab.artshare.common.util.copyNonNullProperties
import com.flab.artshare.storage.StorageService
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import org.springframework.beans.BeanUtils
import org.springframework.beans.BeanWrapperImpl
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ProfileService(
    private val storageService: StorageService,
    private val profileRepo: ProfileRepository
) {
    fun createProfile(uid: String, req: CreateProfileReq): CreateProfileRes {
        validateProfileRequest(req)

        checkProfileExists(uid)

        val imgPath = uploadImg(req.profileImg)

        val profile = profileRepo.save(req.toEntity(uid, imgPath!!))

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

    private fun uploadImg(profileImg: MultipartFile?): String? {
        profileImg?.let { img ->
            return storageService.uploadImage(img)
        }
        return null
    }

    fun findProfile(uid: String): ReadProfileRes{
        val profile = profileRepo.findByUid(uid)
            .orElseThrow { IllegalStateException("Profile with the provided uid is not exists") }
        return ReadProfileRes(profile)
    }

    fun updateProfile(uid: String, req: UpdateProfileReq): UpdateProfileRes{
        val profile = profileRepo.findByUid(uid)
            .orElseThrow { NullPointerException("Profile with the provided uid is not exists") }

        val imgPath = uploadImg(req.profileImg)

        copyNonNullProperties(req.toEntity(imgPath), profile)

        profileRepo.save(profile)

        return UpdateProfileRes(profile)
    }

    fun deleteProfile(uid: String) = profileRepo.deleteByUid(uid)
}
