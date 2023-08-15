plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

val cryptoVersion = "0.1.4"

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
    jvm()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "umi"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation(project(mapOf("path" to ":solana_public_keys")))
                implementation(project(mapOf("path" to ":solana_interfaces")))
                implementation(project(mapOf("path" to ":solana_keypair")))
                implementation("com.diglol.crypto:crypto:$cryptoVersion")
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
    namespace = "com.metaplex.umi_eddsa"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}
