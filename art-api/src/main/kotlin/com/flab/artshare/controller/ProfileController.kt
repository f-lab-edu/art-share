package com.flab.artshare.controller

import com.flab.artshare.application.CreateProfileAndUploadImgService
import com.flab.artshare.config.swagger.docs.ExplainProfileApi
import com.flab.artshare.dto.CreateProfileReq
import com.flab.artshare.dto.CreateProfileRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.Validation

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val createProfileAndUploadImgService: CreateProfileAndUploadImgService,
) : ExplainProfileApi {
    @PostMapping("/me")
    override fun createProfile(
        @AuthenticationPrincipal uid: String,
        @ModelAttribute @Validated req: CreateProfileReq,
    ): ResponseEntity<CreateProfileRes> {
        return ResponseEntity(createProfileAndUploadImgService.execute(uid, req), HttpStatus.CREATED)
    }
}
