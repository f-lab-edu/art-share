package com.flab.artshare.profile

import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class ProfileService(
    private val profileRepo: ProfileRepository,
) {
    fun createProfile(uid: String, req: Profile): Profile {
        validateProfileRequest(req)

        checkProfileExists(uid)

        return profileRepo.save(req)
    }

    private fun checkProfileExists(uid: String) {
        if (profileRepo.existsByUid(uid)) {
            throw IllegalStateException("Profile with the provided uid already exists.")
        }
    }

    private fun validateProfileRequest(req: Profile) {
        if (profileRepo.existsByDisplayNameOrAbout(req.displayName, req.about)) {
            throw IllegalArgumentException("Profile with the provided display name or about already exists.")
        }
    }
}
