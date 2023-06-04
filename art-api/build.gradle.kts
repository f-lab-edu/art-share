description = "api module"
group = "com.flab"
version = "0.0.1-SNAPSHOT"

tasks {
    bootJar { enabled = true }
}

dependencies {
    implementation(project(mapOf("path" to ":art-domain")))
    implementation(project(mapOf("path" to ":art-external")))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.15")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
}
