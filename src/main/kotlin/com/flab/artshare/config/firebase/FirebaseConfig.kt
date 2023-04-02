package com.flab.artshare.config.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.internal.EmulatorCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import kotlin.properties.Delegates

@Configuration
class FirebaseConfig {

    @Value("\${firebase.admin-sdk.path}")
    private lateinit var firebaseAdminSdkPath: String

    @Value("\${firebase.emulator.use_emulator}")
    private var useEmulator :Boolean = true

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
        return if (useEmulator)
            EmulatorCredentials()
        else {
            val file = FileInputStream(File(firebaseAdminSdkPath))
            GoogleCredentials.fromStream(file)
        }
    }
}
