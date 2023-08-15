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

rootProject.name = "umi"
include(":umi")
include(":solana_public_keys")
include(":base58")
include(":solana_eddsa")
include(":solana_interfaces")
include(":solana_keypair")
