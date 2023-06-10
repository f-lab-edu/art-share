package com.flab.artshare.controller

import com.flab.artshare.comment.CommentService
import com.flab.artshare.dto.CreateCommentReq
import com.flab.artshare.dto.CreateCommentRes
import com.flab.artshare.dto.UpdateCommentReq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService,
) {

    @PostMapping
    fun createComment(
        @RequestBody @Valid
        request: CreateCommentReq,
        @PathVariable postId: Long,
    ): ResponseEntity<CreateCommentRes> {
        val comment = commentService.saveComment(postId, request.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateCommentRes.from(comment))
    }

    @PostMapping("/{commentId}")
    fun createReplyComment(
        @RequestBody @Valid
        request: CreateCommentReq,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<CreateCommentRes> {
        val comment = commentService.saveReply(commentId, postId, request.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateCommentRes.from(comment))
    }

    @GetMapping
    fun getAllComment(
        @PathVariable postId: Long,
        @PageableDefault(sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): ResponseEntity<Page<CreateCommentRes>> {
        val comments = commentService.getAllCommentsByPostId(postId, pageable)
        return ResponseEntity.ok(comments.map { CreateCommentRes.from(it) })
    }

    @GetMapping("/{commentId}")
    fun getCommentById(@PathVariable commentId: Long): ResponseEntity<CreateCommentRes> {
        val comment = commentService.getCommentById(commentId)
        return ResponseEntity.ok(CreateCommentRes.from(comment))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody @Valid
        request: UpdateCommentReq,
    ): ResponseEntity<CreateCommentRes> {
        val updatedComment = commentService.updateComment(commentId, request.toEntity())
        return ResponseEntity.ok(CreateCommentRes.from(updatedComment))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long): ResponseEntity<Void> {
        commentService.deleteComment(commentId)
        return ResponseEntity(HttpStatus.OK)
    }
}
