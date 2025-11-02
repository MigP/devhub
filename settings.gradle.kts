pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DevHub"

include(":app")

// Core modules
include(":core:core-common")
include(":core:core-ui")
include(":core:core-network")
include(":core:core-database")
include(":core:core-analytics")

// Feature modules
include(":feature:feature-feed")
include(":feature:feature-profile")
include(":feature:feature-editor")
include(":feature:feature-notifications")
include(":feature:feature-search")

// SDK
include(":sdk-devhub")