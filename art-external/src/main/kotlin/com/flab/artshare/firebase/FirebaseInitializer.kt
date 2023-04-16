package com.flab.artshare.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.internal.EmulatorCredentials
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.FileInputStream
import java.io.IOException

@Configuration
class FirebaseInitializer(private val config: FirebaseConfig) {

    @Bean
    @Throws(IOException::class)
    fun firebaseAuth(): FirebaseAuth {
        val credentials = getCredential()

        val options = FirebaseOptions.builder()
            .setCredentials(credentials)
            .setProjectId("art-share")
            .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }

        return FirebaseAuth.getInstance()
    }

    fun getCredential(): GoogleCredentials {
        println(config)
        return if (config.useEmulator)
            EmulatorCredentials()
        else {
            val file = FileInputStream(File(config.adminSdkPath))
            GoogleCredentials.fromStream(file)
        }
    }
}
