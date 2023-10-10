import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
    kotlin("plugin.serialization") version "1.9.0"
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
            baseName = "readapi"
            xcf.add(this)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(mapOf("path" to ":solanapublickeys")))
                implementation(project(mapOf("path" to ":rpc")))
                implementation(libs.ktor.client.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.rpccore)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val jvmMain by getting
        val jvmTest by getting
    }
}

android {
    namespace = "foundation.metaplex.readapi"
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
    coordinates(group as String, "readapi", version as String)
}

frameworkBundlerConfig {
    frameworkName.set("readapi")
    outputPath.set("$rootDir/XCFrameworkOutputs")
    versionName.set(version as String)
    frameworkType = com.prof18.kmpframeworkbundler.data.FrameworkType.XC_FRAMEWORK
}
