import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.6.21"
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.jetbrains.kotlinx.kover") version "0.7.0-Alpha"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("org.flywaydb.flyway") version "8.4.1" apply false
    kotlin("plugin.jpa") version "1.6.21" apply false
}

allprojects {
    group = "com.flab"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        testImplementation("io.mockk:mockk:1.13.4")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        testImplementation("io.kotest:kotest-runner-junit5:5.4.2")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.boot:spring-boot-starter-web")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    // For ktlint
    tasks {
        // ktlint check task
        val ktlint by creating {
            group = "verification"
            description = "Check Kotlin code style."
            inputs.files(project.files("**/*.kt"))
            doLast {
                project.exec {
                    commandLine("bash", "-c", "./gradlew ktlintCheck")
                }
            }
        }

        // always run ktlint check task before any other task
        val build by getting {
            dependsOn(ktlint)
        }
    }

    kover {
        disabledForProject = false

        useKoverTool()

        excludeInstrumentation {
            classes("com.flab.artshare.ArtShareApplication.kt")
        }
    }

    koverReport {
        filters {
            excludes {
                classes("com.flab.artshare.ArtShareApplication.kt")
            }
            includes {
                packages("com.flab.artshare")
            }
        }

        xml {
            onCheck = false
            setReportFile(file("$rootDir/config/kover-xml-result/report.xml"))
        }

        html {
            title = "ArtShare Coverage Report"
            onCheck = false
            setReportDir(file("$rootDir/config/kover-html-result"))
        }

        verify {
            rule {
                entity = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.APPLICATION

                bound {
                    minValue = 1 // 검증할 커버리지의 최소 범위
                    maxValue = 99 // 검증할 커버리지의 최대 범위
                }
            }
        }
    }
}
