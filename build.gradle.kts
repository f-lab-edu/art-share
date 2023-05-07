import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.6.21"
    id(Plugins.springBoot) version "2.7.9"
    id(Plugins.kover) version "0.7.0-Alpha"
    id(Plugins.detekt) version "1.19.0"
    id("org.flywaydb.flyway") version "8.4.1" apply false
    kotlin("plugin.jpa") version "1.6.21" apply false
    id(Plugins.ktlint) version "11.2.0" // 추가!!
}

allprojects {
    group = "com.flab"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = Plugins.springBoot)
    apply(plugin = Plugins.springDependencyManagement)
    apply(plugin = Plugins.kover)
    apply(plugin = Plugins.ktlint)
    apply(plugin = Plugins.kotlinSpring)
    apply(plugin = Plugins.kotlinJvm)

    // Apply Detekt plugin to all subprojects
    apply(plugin = Plugins.detekt)

    detekt {
        source.setFrom(files("src/main/kotlin"))
        config.setFrom(files("$rootDir/config/detekt.yml"))
        parallel = true
        buildUponDefaultConfig = true
        allRules = false
        disableDefaultRuleSets = false
    }

    val detektFormat by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
        // detekt 포맷팅을 위한 task
        config.setFrom(files(projectDir.resolve("config/detekt/detekt.yml")))
    }

    val detektAll by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
        // detekt 정적 분석을 돌리고 그에 대한 성공 / 실패 여부를 반환하는 task
        config.setFrom(files(projectDir.resolve("config/detekt/detekt.yml")))
    }

    dependencies {
        testImplementation(Dependencies.mockk)
        implementation(Dependencies.jacksonModuleKotlin)
        developmentOnly(Dependencies.springBootDevtools)
        testImplementation(Dependencies.kotestRunnerJUnit5)
        testImplementation(Dependencies.kotestExtensionsSpring)
        testImplementation(Dependencies.springBootStarterTest)
        implementation(Dependencies.springBootStarterWeb)
    }

    tasks {
        jar { enabled = true }
        bootJar { enabled = false }
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

    ktlint {
        ignoreFailures.set(false)

        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
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
