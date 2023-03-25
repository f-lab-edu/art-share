import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.jetbrains.kotlinx.kover") version "0.7.0-Alpha"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("org.flywaydb.flyway") version "8.3.0"
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "com.flab"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.google.firebase:firebase-admin:9.1.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.2")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.1")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.15")
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

tasks.register<Copy>("updateLib") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations["compileClasspath"])
    into("libs/")
}

tasks.register("buildAndReload") {
    group = "application"
    description = "Builds the project and restarts the application using the run.sh script."

    dependsOn("build")

    mustRunAfter("build")

    doLast {
        File(".", "reload.txt").writeText("${System.currentTimeMillis()}")
    }
}

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

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    val activeProfile = System.getProperty("spring.profiles.active")
    systemProperty("spring.profiles.active", activeProfile)
}
