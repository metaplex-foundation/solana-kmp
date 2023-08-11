plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version("8.0.2").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
}
val cryptoVersion = "0.1.4"

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
