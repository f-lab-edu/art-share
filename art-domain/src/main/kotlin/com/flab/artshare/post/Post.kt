package com.flab.artshare.post

import com.flab.artshare.comment.Comment
import com.flab.artshare.common.audit.BaseEntity
import javax.persistence.*

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var title: String,
    var content: String,
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<Comment> = ArrayList(),
) : BaseEntity() {

    fun updatePost(title: String?, content: String?) {
        title?.let { this.title = it }
        content?.let { this.content = it }
    }
}
