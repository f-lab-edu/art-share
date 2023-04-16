package com.flab.artshare

import com.flab.artshare.firebase.FirebaseService
import org.springframework.stereotype.Service

@Service
class AuthService(private val firebaseService: FirebaseService) {
    fun getUserId(token: String): String = this.firebaseService.verifyToken(token)
}
