package com.flab.artshare.profile

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProfileRepository : JpaRepository<Profile, Long> {
    fun existsByDisplayNameOrAbout(displayName: String, about: String): Boolean
    fun existsByUid(uid: String): Boolean
}
