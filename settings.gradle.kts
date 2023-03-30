dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "SmartHome"
include(":app")
include(":common:core")
include(":common:data")
include(":common:network")
include(":common:shared_preferences")
include(":common:permissions")
include(":common:storage")
