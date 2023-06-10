package com.flab.artshare.post

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
) {

    fun savePost(req: Post): Post {
        return postRepository.save(req)
    }

    @Transactional(readOnly = true)
    fun getAllPosts(): List<Post> {
        return postRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getPostById(id: Long): Post {
        return findPostOrThrow(id)
    }

    fun updatePost(id: Long, request: Post): Post {
        val post = findPostOrThrow(id)
        post.updatePost(request.title, request.content)
        return postRepository.save(post)
    }

    fun deletePost(id: Long) {
        postRepository.deleteById(id)
    }

    private fun findPostOrThrow(id: Long): Post {
        return postRepository.findById(id).orElseThrow { NoSuchElementException("Post not found with id: $id") }
    }
}
