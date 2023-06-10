package com.flab.artshare.dto

import com.flab.artshare.post.Post
import javax.validation.constraints.NotNull

data class CreatePostReq(

    @field:NotNull(message = "제목은 필수 입력값입니다.")
    val title: String,

    @field:NotNull(message = "내용은 필수 입력값입니다.")
    val content: String,
) {
    fun toEntity(): Post {
        return Post(
            title = title,
            content = content,
        )
    }
}
