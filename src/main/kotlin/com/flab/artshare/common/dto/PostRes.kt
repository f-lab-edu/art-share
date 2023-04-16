package com.flab.artshare.common.dto

import com.flab.artshare.post.Post
import java.time.LocalDateTime

data class PostRes(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(post: Post): PostRes {
            return PostRes(
                id = post.id!!,
                title = post.title,
                content = post.content,
                createdAt = post.createdAt,
            )
        }
    }
}
