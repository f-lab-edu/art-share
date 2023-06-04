package com.flab.artshare

import com.flab.artshare.firebase.FirebaseApi
import org.springframework.stereotype.Service

@Service
class AuthService(private val firebaseApi: FirebaseApi) {
    fun getUserId(token: String): String {
        return this.firebaseApi.verifyToken(token)
    }
}
