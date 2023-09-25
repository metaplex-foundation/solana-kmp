buildscript {
    dependencies {
        classpath(libs.gradle)
    }
}
plugins {
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.maven.publish).apply(false)
    alias(libs.plugins.kmp.framework.bundler).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
