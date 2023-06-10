package com.flab.artshare.firebase

import com.google.firebase.auth.FirebaseAuth
import org.springframework.stereotype.Service

@Service
class FirebaseApi(private val firebaseAuth: FirebaseAuth) {
    fun verifyToken(token: String): String = this.firebaseAuth.verifyIdToken(token).uid
}
