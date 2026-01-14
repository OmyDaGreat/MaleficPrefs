import cn.lalaki.pub.BaseCentralPortalPlusExtension.PublishingType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

val user = "MaleficCompose"
val repo = "MaleficPrefs"
val g = "xyz.malefic.compose"
val artifact = "prefs"
val v = "1.3.1"
val desc = "A library for managing preferences and settings based on Java's Preferences API"

val localMavenRepo = uri(layout.buildDirectory.dir("repo").get())

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.central)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
}

group = g
version = v

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(17))
    }

    jvm()

    js(IR) {
        browser()
        nodejs()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmWasi {
        nodejs()
    }

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    watchosX64()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    watchosDeviceArm64()

    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()

    macosX64()
    macosArm64()

    linuxX64()
    linuxArm64()

    mingwX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val wasmJsMain by getting
        val wasmJsTest by getting
        val wasmWasiMain by getting
        val wasmWasiTest by getting
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val nativeTest by creating {
            dependsOn(commonTest)
        }

        val androidNativeMain by creating {
            dependsOn(nativeMain)
        }
        val androidNativeArm32Main by getting {
            dependsOn(androidNativeMain)
        }
        val androidNativeArm64Main by getting {
            dependsOn(androidNativeMain)
        }
        val androidNativeX86Main by getting {
            dependsOn(androidNativeMain)
        }
        val androidNativeX64Main by getting {
            dependsOn(androidNativeMain)
        }

        val appleMain by creating {
            dependsOn(nativeMain)
        }

        val iosMain by creating {
            dependsOn(appleMain)
        }
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        val watchosMain by creating {
            dependsOn(appleMain)
        }
        val watchosX64Main by getting {
            dependsOn(watchosMain)
        }
        val watchosArm32Main by getting {
            dependsOn(watchosMain)
        }
        val watchosArm64Main by getting {
            dependsOn(watchosMain)
        }
        val watchosSimulatorArm64Main by getting {
            dependsOn(watchosMain)
        }
        val watchosDeviceArm64Main by getting {
            dependsOn(watchosMain)
        }

        val tvosMain by creating {
            dependsOn(appleMain)
        }
        val tvosX64Main by getting {
            dependsOn(tvosMain)
        }
        val tvosArm64Main by getting {
            dependsOn(tvosMain)
        }
        val tvosSimulatorArm64Main by getting {
            dependsOn(tvosMain)
        }

        val macosMain by creating {
            dependsOn(appleMain)
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }

        val linuxMain by creating {
            dependsOn(nativeMain)
        }
        val linuxX64Main by getting {
            dependsOn(linuxMain)
        }
        val linuxArm64Main by getting {
            dependsOn(linuxMain)
        }

        val mingwMain by creating {
            dependsOn(nativeMain)
        }
        val mingwX64Main by getting {
            dependsOn(mingwMain)
        }
    }
}

publishing {
    publications.withType<MavenPublication> {
        groupId = g
        artifactId = if (name == "kotlinMultiplatform") artifact else "$artifact-$name"
        version = v

        pom {
            name.set(repo)
            description.set(desc)
            url.set("https://github.com/$user/$repo")
            developers {
                developer {
                    name.set("Om Gupta")
                    email.set("ogupta4242@gmail.com")
                }
            }
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/$user/$repo.git")
                developerConnection.set("scm:git:ssh://github.com/$user/$repo.git")
                url.set("https://github.com/$user/$repo")
            }
        }
    }
    repositories {
        maven {
            url = localMavenRepo
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

centralPortalPlus {
    url = localMavenRepo
    username = System.getenv("centralPortalUsername") ?: ""
    password = System.getenv("centralPortalPassword") ?: ""
    publishingType = PublishingType.AUTOMATIC
}

tasks.apply {
    register("formatAndLintKotlin") {
        group = "formatting"
        description = "Fix Kotlin code style deviations with kotlinter"
        dependsOn("formatKotlin")
        dependsOn("lintKotlin")
    }
    build {
        dependsOn(dokkaGenerate)
        dependsOn(named("formatAndLintKotlin"))
    }
}

dokka {
    dokkaPublications.html {
        outputDirectory.set(layout.buildDirectory.dir("dokka"))
    }
}
