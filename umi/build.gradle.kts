plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

val bufferVersion = "1.3.0"
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
                implementation(project(mapOf("path" to ":umi_public_keys")))
                implementation(project(mapOf("path" to ":base58")))
                implementation("com.ditchoom:buffer:$bufferVersion")
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
    namespace = "com.metaplex.umi"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}