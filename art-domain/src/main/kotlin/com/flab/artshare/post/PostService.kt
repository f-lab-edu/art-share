package com.flab.artshare.post

import com.flab.artshare.common.dto.CreatePostReq
import com.flab.artshare.common.dto.PostRes
import com.flab.artshare.common.dto.UpdatePostReq
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
) {

    fun savePost(request: CreatePostReq): Post {
        val post = request.toEntity()
        return postRepository.save(post)
    }

    @Transactional(readOnly = true)
    fun getAllPosts(): List<PostRes> {
        val posts = postRepository.findAll()
        return posts.map { PostRes.from(it) }
    }

    @Transactional(readOnly = true)
    fun getPostById(id: Long): PostRes {
        val post = findPostOrThrow(id)
        return PostRes.from(post)
    }

    fun updatePost(id: Long, request: UpdatePostReq): PostRes {
        val post = findPostOrThrow(id)
        post.updatePost(request.title, request.content)
        val updatedPost = postRepository.save(post)
        return PostRes.from(updatedPost)
    }

    fun deletePost(id: Long) {
        postRepository.deleteById(id)
    }

    private fun findPostOrThrow(id: Long): Post {
        return postRepository.findById(id).orElseThrow { NoSuchElementException("Post not found with id: $id") }
    }
}
