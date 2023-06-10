package com.flab.artshare.dto

import com.flab.artshare.post.Post
import java.time.LocalDateTime

data class CreatePostRes(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(post: Post): CreatePostRes {
            return CreatePostRes(
                id = post.id!!,
                title = post.title,
                content = post.content,
                createdAt = post.createdAt,
            )
        }
    }
}
