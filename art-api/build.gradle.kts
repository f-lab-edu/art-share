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
    testImplementation(Dependencies.mockk)
    testImplementation(Dependencies.kotestRunnerJUnit5)
    testImplementation(Dependencies.kotestExtensionsSpring)
    implementation(Dependencies.springBootStarterDataJpa)
    implementation(Dependencies.jacksonModuleKotlin)
    testImplementation(Dependencies.springBootStarterTest)
    implementation(Dependencies.micrometerCore)
    implementation(Dependencies.springBootStarterActuator)
}
