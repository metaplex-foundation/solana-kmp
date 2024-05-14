import org.jetbrains.kotlin.gradle.plugin.extraProperties

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

subprojects.forEach { project ->
    project.afterEvaluate {
        project.tasks.filterIsInstance<Test>().forEach { testTask ->
            val includeIntegrationTests = if (project.hasProperty("includeIntegrationTests")) {
                project.property("includeIntegrationTests") != "false"
            } else if (project.hasProperty("excludeIntegrationTests")) {
                project.property("excludeIntegrationTests") == "false"
            } else true

            if (!includeIntegrationTests) {
                testTask.exclude("**/*IntegTest*")
            }
        }
    }
}
