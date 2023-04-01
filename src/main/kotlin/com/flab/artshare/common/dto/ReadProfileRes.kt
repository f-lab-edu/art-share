package com.flab.artshare.common.dto

import com.flab.artshare.profile.Profile

data class ReadProfileRes(
    val displayName: String,
    val about: String,
    val imgPath: String
) {
    constructor(profile: Profile) : this(profile.displayName, profile.about, profile.imgPath)
}
