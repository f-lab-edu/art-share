package com.flab.art_share.config.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value
import java.io.File
import java.io.FileInputStream
import java.io.IOException

@Configuration
class FirebaseConfig {

    @Value("\${firebase.admin-sdk.path}")
    private lateinit var firebaseAdminSdkPath: String

    @Bean
    @Throws(IOException::class)
    fun firebaseAuth(): FirebaseAuth {

        val serviceAccount = FileInputStream(File(firebaseAdminSdkPath))
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }

        return FirebaseAuth.getInstance()
    }
}
