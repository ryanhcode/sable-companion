val modName = "sable-companion"

rootProject.name = modName

includeBuild("build-logic")

// Include subprojects
rootDir.listFiles {
    it.isDirectory
            && it.name != "build-logic"
            && (it.resolve("build.gradle").exists() || it.resolve("build.gradle.kts").exists())
}.forEach {
    var projectName = ":${modName}-${it.toRelativeString(rootDir)}"
    include(projectName)
    project(projectName).projectDir = it
}

// Import version catalog
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))
        }
    }
}

pluginManagement.repositories {
    mavenCentral()
    exclusiveContent {
        forRepository { maven("https://maven.fabricmc.net") }
        filter {
            includeGroup("net.fabricmc")
            includeGroup("net.fabricmc.fabric-loom")
            includeGroup("net.fabricmc.fabric-loom-remap")
            includeGroup("net.fabricmc.unpick")
        }
    }
}