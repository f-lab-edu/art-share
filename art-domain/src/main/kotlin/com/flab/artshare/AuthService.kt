package com.flab.artshare

import com.flab.artshare.firebase.FirebaseApi
import org.springframework.stereotype.Service

@Service
class AuthService(private val firebaseService: FirebaseApi) {
    fun getUserId(token: String): String = this.firebaseService.verifyToken(token)
}
