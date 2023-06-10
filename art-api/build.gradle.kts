description = "api module"
group = "com.flab"
version = "0.0.1-SNAPSHOT"

tasks {
    bootJar { enabled = true }
}

dependencies {
    implementation(project(mapOf("path" to ":art-domain")))
    implementation(project(mapOf("path" to ":art-external")))
    implementation(Dependencies.springBootStarterWeb)
    implementation(Dependencies.springBootStarterSecurity)
    testImplementation(Dependencies.springSecurityTest)
    implementation(Dependencies.springdocOpenapiUi)
    implementation(Dependencies.springBootStarterDataJpa)
    implementation(Dependencies.micrometerCore)
    implementation(Dependencies.springBootStarterActuator)
}
