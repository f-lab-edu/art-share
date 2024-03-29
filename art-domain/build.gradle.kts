description = "domain module"

plugins {
    id("org.flywaydb.flyway") version "8.4.1"
    kotlin("plugin.jpa") version "1.6.21"
}

dependencies {
    implementation(project(mapOf("path" to ":art-external")))
    implementation(Dependencies.springBootStarterDataJpa)
    runtimeOnly(Dependencies.mariadbJavaClient)
    implementation(Dependencies.flywayCore)
    implementation(Dependencies.flywayMysql)
    implementation("mysql:mysql-connector-java:8.0.27")
}
