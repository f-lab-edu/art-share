package com.flab.artshare.common.audit

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedBy
    @Column(name = "created_by", length = 15)
    var createdBy: String? = null
        protected set

    @LastModifiedBy
    @Column(name = "updated_by", length = 15)
    var modifiedBy: String? = null
        protected set

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATE")
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "DATE")
    var modifiedDate: LocalDateTime = LocalDateTime.now()
        protected set
}
