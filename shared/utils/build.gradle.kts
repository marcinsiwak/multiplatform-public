import pl.msiwak.multiplatfor.dependencies.Deps

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Utils Shared Module"
        homepage = "https://github.com/marcinsiwak/multiplatform"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "utils"

        }
        pod("GoogleSignIn")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Deps.Firebase) {
                    api(authentication)
                    api(crashlytics)
                }
                with(Deps.Kotlinx) {
                    api(dateTime)
                    api(coroutines)
                }
                with(Deps.Napier) {
                    api(napier)
                }
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "pl.msiwak.multiplatform.utils"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}