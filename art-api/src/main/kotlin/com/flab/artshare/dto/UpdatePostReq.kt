package com.flab.artshare.dto

import com.flab.artshare.post.Post

data class UpdatePostReq(
    val title: String,
    val content: String,
) {
    fun toEntity(): Post {
        return Post(
            title = title,
            content = content,
        )
    }
}
