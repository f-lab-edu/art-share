package com.flab.artshare.unit

import com.flab.artshare.common.dto.CreateProfileReq
import com.flab.artshare.profile.Profile
import com.flab.artshare.profile.ProfileRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.assertions.throwables.shouldThrow
import org.springframework.mock.web.MockMultipartFile
import com.flab.artshare.profile.ProfileService
import com.flab.artshare.storage.StorageService
import io.kotest.core.spec.style.AnnotationSpec.Test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk

@Test()
class ProfileServiceTest : BehaviorSpec({
    val storageService = mockk<StorageService>()
    val profileRepo = mockk<ProfileRepository>()
    val profileService = ProfileService(storageService, profileRepo)

    val uid = "test-uid"

    val bytes = "test".toByteArray()
    val multipartFile = MockMultipartFile("file", "test.jpg", "text/plain", bytes)

    given("a create profile request") {
        val request = CreateProfileReq("John", "Hi! Nice to meet you bro!", multipartFile)

        `when`("I create user profile successfully") {

            every { storageService.uploadImage(any()) } returns "mock-url"
            every { profileRepo.existsByUid(any()) } returns false
            every { profileRepo.existsByDisplayNameOrAbout(any(), any()) } returns false
            every { profileRepo.save(any()) } returns Profile(1L, uid, "John", "Hi! Nice to meet you bro!", "")

            val profile = profileService.createProfile(uid, request)

            then("I should receive the created profile") {
                profile shouldNotBe null
                profile.displayName shouldBe request.displayName
                profile.about shouldBe request.about
                profile.imgPath shouldNotBe null
            }
        }
    }

    given("a already exists name or about") {
        val request = CreateProfileReq("John", "Hi! Nice to meet you bro!", multipartFile)

        `when`("I create user profile") {

            every { storageService.uploadImage(any()) } returns "mock-url"
            every { profileRepo.existsByUid(any()) } returns false
            every { profileRepo.existsByDisplayNameOrAbout(any(), any()) } returns true

            then("throw IllegalArgumentException") {
                shouldThrow<IllegalArgumentException> {
                    profileService.createProfile(uid, request)
                }
            }
        }
    }

    given("a already exists profile") {
        val request = CreateProfileReq("John", "Hi! Nice to meet you bro!", multipartFile)

        `when`("I create user profile") {

            every { storageService.uploadImage(any()) } returns "mock-url"
            every { profileRepo.existsByUid(any()) } returns true

            then("throw IllegalArgumentException") {
                shouldThrow<IllegalArgumentException> {
                    profileService.createProfile(uid, request)
                }
            }
        }
    }
})
