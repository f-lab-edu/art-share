package com.flab.artshare.config.swagger.docs

import com.flab.artshare.common.dto.CreateProfileReq
import com.flab.artshare.common.dto.CreateProfileRes
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ModelAttribute

interface ExplainProfileApi {
    @Operation(summary = "Create User Profile")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Success",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CreateProfileRes::class)
                    )
                ]
            ),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()]),
            ApiResponse(responseCode = "400", description = "User Already Has Profile", content = [Content()])
        ]
    )
    fun createProfile(
        @Parameter(description = "User ID") @AuthenticationPrincipal uid: String,
        @Parameter(description = "Profile Info") @ModelAttribute req: CreateProfileReq
    ): ResponseEntity<CreateProfileRes>
}
