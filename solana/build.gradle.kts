import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
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
            baseName = "solana"
            xcf.add(this)

            export(project(":amount"))
            export(project(":base58"))
            export(project(":readapi"))
            export(project(":amount"))
            export(project(":rpc"))
            export(project(":signer"))
            export(project(":solanaeddsa"))
            export(project(":solanapublickeys"))
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(mapOf("path" to ":amount")))
                api(project(mapOf("path" to ":base58")))
                api(project(mapOf("path" to ":readapi")))
                api(project(mapOf("path" to ":rpc")))
                api(project(mapOf("path" to ":signer")))
                api(project(mapOf("path" to ":solanaeddsa")))
                api(project(mapOf("path" to ":solanapublickeys")))
                implementation(libs.buffer)
                implementation(libs.kborsh)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        val jvmMain by getting
        val jvmTest by getting
    }
}

android {
    namespace = "foundation.metaplex.solana"
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
    coordinates(group as String, "solana", version as String)
}

frameworkBundlerConfig {
    frameworkName.set("solana")
    outputPath.set("$rootDir/XCFrameworkOutputs")
    versionName.set(version as String)
    frameworkType = com.prof18.kmpframeworkbundler.data.FrameworkType.XC_FRAMEWORK
}
