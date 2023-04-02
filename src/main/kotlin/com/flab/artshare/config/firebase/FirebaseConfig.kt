package com.flab.artshare.config.firebase

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "firebase")
data class FirebaseConfig(
    var adminSdkPath: String = "dummy",
    var useEmulator: Boolean = true
)
