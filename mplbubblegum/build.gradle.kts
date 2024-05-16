import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    kotlin("plugin.serialization") version "1.9.0"
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.kmp.framework.bundler)
}

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

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "mplbubblegum"
            xcf.add(this)
            export(project(":solana"))
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(mapOf("path" to ":solana")))
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kborsh)
                implementation(libs.buffer)
                implementation(libs.web3solana)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}

android {
    namespace = "foundation.metaplex.mplbubblegum"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    coordinates(group as String, "mplbubblegum", version as String)
}

frameworkBundlerConfig {
    frameworkName.set("mplbubblegum")
    outputPath.set("$rootDir/XCFrameworkOutputs")
    versionName.set(version as String)
    frameworkType = com.prof18.kmpframeworkbundler.data.FrameworkType.XC_FRAMEWORK
}
