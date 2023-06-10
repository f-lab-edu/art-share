package com.flab.artshare.dto

import com.flab.artshare.comment.Comment
import java.time.LocalDateTime

data class CreateCommentRes(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(comment: Comment): CreateCommentRes {
            return CreateCommentRes(
                id = comment.id!!,
                content = comment.content,
                createdAt = comment.createdAt,
            )
        }
    }
}
