plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

val bufferVersion = "1.3.0"
val cryptoVersion = "0.1.4"
val kotlinxCoroutines = "1.7.3"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
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
                implementation(project(mapOf("path" to ":solana_eddsa")))
                implementation(project(mapOf("path" to ":solana_keypair")))
                implementation(project(mapOf("path" to ":solana_public_keys")))
                implementation(project(mapOf("path" to ":solana_interfaces")))
                implementation(project(mapOf("path" to ":base58")))
                implementation("com.ditchoom:buffer:$bufferVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutines")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutines")
            }
        }
        val jvmMain by getting
        val jvmTest by getting
    }
}

android {
    namespace = "com.metaplex.umi"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
