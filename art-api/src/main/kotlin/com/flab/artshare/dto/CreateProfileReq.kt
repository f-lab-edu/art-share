package com.flab.artshare.dto

import com.flab.artshare.profile.Profile
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CreateProfileReq(
    @field:Size(max = 10, message = "Display name must be 10 characters or less")
    @field:Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "Display name must contain only Korean and English characters")
    val displayName: String,

    @field:Size(max = 50, message = "About must be 50 characters or less")
    @field:Pattern(
        regexp = "^[가-힣a-zA-Z0-9\\p{Punct}]*$",
        message = "About must contain only Korean, English characters, numbers and punctuation",
    )
    val about: String,

    @field:NotEmpty
    val profileImg: MultipartFile,
) {
    fun toEntity(uid: String, imgPath: String): Profile {
        return Profile(null, uid, this.displayName, this.about, imgPath)
    }
}
