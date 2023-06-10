package com.flab.artshare.comment

import com.flab.artshare.common.audit.BaseEntity
import com.flab.artshare.post.Post
import javax.persistence.*

@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Comment? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = null,
) : BaseEntity() {
    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    var children: MutableList<Comment> = ArrayList()

    fun addReply(reply: Comment) {
        this.children.add(reply)
        reply.parent = this
    }

    fun updateComment(content: String?) {
        content?.let { this.content = it }
    }
}
