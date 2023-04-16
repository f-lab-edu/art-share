package com.flab.artshare.controller

import com.flab.artshare.common.dto.CreatePostReq
import com.flab.artshare.common.dto.PostRes
import com.flab.artshare.common.dto.UpdatePostReq
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PostController(
    private val postService: PostService,
) {

    @PostMapping("/post")
    fun createPost(
        @RequestBody @Valid
        request: CreatePostReq,
    ): ResponseEntity<Void> {
        postService.savePost(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/post/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<PostRes> {
        val postRes = postService.getPostById(id)
        return ResponseEntity.ok(postRes)
    }

    @GetMapping("/posts")
    fun getAllPosts(): ResponseEntity<List<PostRes>> {
        val posts = postService.getAllPosts()
        return ResponseEntity.ok(posts)
    }

    @PutMapping("/post/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody
        @Valid
        request: UpdatePostReq,
    ): ResponseEntity<PostRes> {
        val updatedPost = postService.updatePost(id, request)
        return ResponseEntity.ok(updatedPost)
    }

    @DeleteMapping("/post/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Void> {
        postService.deletePost(id)
        return ResponseEntity.noContent().build()
    }
}
