description = "external module"

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

dependencies {
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.435")
    implementation("com.google.firebase:firebase-admin:9.1.1")
}
