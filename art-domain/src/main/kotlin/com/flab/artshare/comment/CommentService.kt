package com.flab.artshare.comment

import com.flab.artshare.post.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val postService: PostService,
) {

    /** 댓글 */
    fun saveComment(postId: Long, request: Comment): Comment {
        val post = postService.getPostById(postId)
        request.post = post
        return commentRepository.save(request)
    }

    /** 대댓글 */
    fun saveReply(parentId: Long, postId: Long, request: Comment): Comment {
        val parentComment = findCommentOrThrow(parentId)
        val post = postService.getPostById(postId)
        request.post = post
        parentComment.addReply(request)
        return commentRepository.save(request)
    }

    @Transactional(readOnly = true)
    fun getAllComments(): Page<Comment> {
        return commentRepository.findAllByParentIsNull(Pageable.unpaged())
    }

    @Transactional(readOnly = true)
    fun getAllCommentsByPostId(parentId: Long, pageable: Pageable): Page<Comment> {
        return commentRepository.findAllByPost_Id(parentId, Pageable.unpaged())
    }

    @Transactional(readOnly = true)
    fun getCommentById(id: Long): Comment {
        return findCommentOrThrow(id)
    }

    fun updateComment(id: Long, request: Comment): Comment {
        val comment = findCommentOrThrow(id)
        comment.updateComment(request.content)
        return commentRepository.save(comment)
    }

    fun deleteComment(id: Long) {
        commentRepository.deleteById(id)
    }

    private fun findCommentOrThrow(id: Long): Comment {
        return commentRepository.findById(id).orElseThrow { NoSuchElementException("Comment not found with id: $id") }
    }
}
