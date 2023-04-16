package com.flab.artshare.post

import com.flab.artshare.common.audit.BaseEntity
import javax.persistence.*

@Entity
class Post constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, name = "title", length = 50)
    var title: String,

    @Column(nullable = false, name = "content", length = 500, columnDefinition = "TEXT")
    var content: String,
) : BaseEntity() {

    fun updatePost(title: String?, content: String?) {
        title?.let { this.title = it }
        content?.let { this.content = it }
    }
}
