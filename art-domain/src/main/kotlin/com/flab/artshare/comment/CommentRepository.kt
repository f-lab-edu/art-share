package com.flab.artshare.comment

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByParentIsNull(pageable: Pageable): Page<Comment>
    fun findAllByPost_Id(postId: Long, pageable: Pageable): Page<Comment>
}
