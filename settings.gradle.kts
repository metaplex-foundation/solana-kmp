pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "solana"
include(":solana")
include(":solanapublickeys")
include(":base58")
include(":solanaeddsa")
include(":solanainterfaces")
include(":solanakeypair")
include(":amount")
include(":readapi")
