package com.flab.artshare.profile

import com.flab.artshare.common.dto.*
import com.flab.artshare.config.swagger.docs.ExplainProfileApi
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(private val profileService: ProfileService) : ExplainProfileApi {
    @PostMapping("/me")
    override fun createProfile( @AuthenticationPrincipal uid: String,
                                @ModelAttribute req: CreateProfileReq
    ): ResponseEntity<CreateProfileRes> {
        val result = this.profileService.createProfile(uid, req)
        return ResponseEntity(result, HttpStatus.CREATED)
    }

    @GetMapping("/me")
    override fun readProfile(@AuthenticationPrincipal uid: String): ResponseEntity<ReadProfileRes> {
        val result = this.profileService.findProfile(uid)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @PutMapping("/me")
    override fun updateProfile(@AuthenticationPrincipal uid: String,
                               @ModelAttribute req: UpdateProfileReq): ResponseEntity<UpdateProfileRes> {
        val result = this.profileService.updateProfile(uid, req)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @DeleteMapping("/me")
    override fun deleteProfile(@AuthenticationPrincipal uid: String): ResponseEntity<Unit> {
        this.profileService.deleteProfile(uid)
        return ResponseEntity(HttpStatus.OK)
    }
}
