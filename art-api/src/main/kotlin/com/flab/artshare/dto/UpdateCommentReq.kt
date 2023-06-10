package com.flab.artshare.dto

import com.flab.artshare.comment.Comment

data class UpdateCommentReq(
    val content: String,
) {
    fun toEntity(): Comment {
        return Comment(
            content = content,
        )
    }
}
