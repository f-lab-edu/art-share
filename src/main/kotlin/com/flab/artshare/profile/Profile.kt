package com.flab.artshare.profile

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Profile(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val uid: String,
    val displayName: String,
    val about: String,
    val imgPath: String
)
