package com.flab.artshare.dto

import com.flab.artshare.comment.Comment
import javax.validation.constraints.NotNull

data class CreateCommentReq(
    @field:NotNull(message = "댓글 내용을 입력해주세요.")
    val content: String,
) {
    fun toEntity(): Comment {
        return Comment(
            content = content,
        )
    }
}
