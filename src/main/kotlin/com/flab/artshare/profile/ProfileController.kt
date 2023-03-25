package com.flab.artshare.profile

import com.flab.artshare.common.dto.CreateProfileReq
import com.flab.artshare.common.dto.CreateProfileRes
import com.flab.artshare.config.swagger.docs.ExplainProfileApi
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val profileService: ProfileService
) : ExplainProfileApi {
    @PostMapping("/me")
    override fun createProfile(
        @AuthenticationPrincipal uid: String,
        @ModelAttribute req: CreateProfileReq
    ): ResponseEntity<CreateProfileRes> {
        val result = this.profileService.createProfile(uid, req)
        return ResponseEntity(result, HttpStatus.CREATED)
    }
}
