package com.flab.artshare.controller

import com.flab.artshare.config.swagger.docs.ExplainProfileApi
import com.flab.artshare.dto.CreateProfileReq
import com.flab.artshare.dto.CreateProfileRes
import com.flab.artshare.naverCloud.NaverStorageService
import com.flab.artshare.profile.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val naverStorageService: NaverStorageService,
    private val profileService: ProfileService
) : ExplainProfileApi {
    @PostMapping("/me")
    override fun createProfile( @AuthenticationPrincipal uid: String,
                                @ModelAttribute req: CreateProfileReq
    ): ResponseEntity<CreateProfileRes> {
        val imgPath = this.naverStorageService.uploadImage(req.profileImg)
        val result = this.profileService.createProfile(uid, req.toEntity(uid, imgPath))
        return ResponseEntity(CreateProfileRes(result), HttpStatus.CREATED)
    }
}
