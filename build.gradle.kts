buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
    }
}
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version("8.0.2").apply(false)
    kotlin("multiplatform").version("1.9.0").apply(false)
    id("com.vanniktech.maven.publish").version("0.25.3").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
