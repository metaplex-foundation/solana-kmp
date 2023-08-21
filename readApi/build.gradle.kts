plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.android.library")
}

val bufferVersion = "1.3.0"
val ktorVersion = "2.3.3"
val serializationVersion = "1.6.0-RC"
val kotlinxCoroutines = "1.7.3"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
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
                implementation(project(mapOf("path" to ":solana_public_keys")))
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutines")
                implementation("com.github.lamba92:KRandomStrings:1.0.1")
                implementation("io.github.funkatronics:rpccore:0.2.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutines")
            }
        }
    }
}

android {
    namespace = "com.metaplex.base58"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}