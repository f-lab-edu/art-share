package com.flab.artshare.controller

import com.flab.artshare.dto.CreatePostReq
import com.flab.artshare.dto.CreatePostRes
import com.flab.artshare.dto.UpdatePostReq
import com.flab.artshare.post.PostService
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
    ): ResponseEntity<CreatePostRes> {
        val post = postService.savePost(request.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePostRes.from(post))
    }

    @GetMapping("/post/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<CreatePostRes> {
        val post = postService.getPostById(id)
        return ResponseEntity.ok(CreatePostRes.from(post))
    }

    @GetMapping("/posts")
    fun getAllPosts(): ResponseEntity<List<CreatePostRes>> {
        val posts = postService.getAllPosts()
        return ResponseEntity.ok(posts.map { CreatePostRes.from(it) })
    }

    @PutMapping("/post/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody
        @Valid
        request: UpdatePostReq,
    ): ResponseEntity<CreatePostRes> {
        val updatedPost = postService.updatePost(id, request.toEntity())
        return ResponseEntity.ok(CreatePostRes.from(updatedPost))
    }

    @DeleteMapping("/post/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Void> {
        postService.deletePost(id)
        return ResponseEntity.noContent().build()
    }
}
