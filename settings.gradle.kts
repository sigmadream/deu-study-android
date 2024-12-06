pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Android Study"
include(":app")
include(":ch06")
include(":ch07")
include(":ch08")
include(":ch09")
include(":ch10")
include(":ch11")
include(":ch12")
include(":ch13")
include(":ch14")
include(":ch17")
include(":todo")
include(":todo_orm")
include(":ch15")
include(":ch16")
include(":ch18")
