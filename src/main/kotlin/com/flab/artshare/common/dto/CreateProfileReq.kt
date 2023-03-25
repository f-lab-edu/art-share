package com.flab.artshare.common.dto

import com.flab.artshare.profile.Profile
import org.springframework.web.multipart.MultipartFile

data class CreateProfileReq(
    val displayName: String,
    val about: String,
    val profileImg: MultipartFile
) {
    fun toEntity(uid: String, imgPath: String): Profile {
        return Profile(null, uid, this.displayName, this.about, imgPath)
    }
}
