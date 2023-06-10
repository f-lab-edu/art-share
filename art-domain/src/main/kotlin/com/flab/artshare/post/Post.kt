package com.flab.artshare.post

import com.flab.artshare.common.audit.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Post constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var title: String,
    var content: String,
) : BaseEntity() {

    fun updatePost(title: String?, content: String?) {
        title?.let { this.title = it }
        content?.let { this.content = it }
    }
}
