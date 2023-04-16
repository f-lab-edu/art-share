description = "api module"
group = "com.flab"
version = "0.0.1-SNAPSHOT"

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

dependencies {
    implementation(project(mapOf("path" to ":art-domain")))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.15")
}
