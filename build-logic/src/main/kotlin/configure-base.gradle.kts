plugins {
    `java-library`
    `maven-publish`
}

group = rootProject.group
version = rootProject.version

val libs: VersionCatalog = versionCatalogs.named("libs");
fun versionOf(name: String): String = libs.findVersion(name).get().toString()
fun gradleProperty(name: String): String = findProperty(name) as String

java {
    toolchain.languageVersion = JavaLanguageVersion.of(versionOf("java-version"))
    withSourcesJar()
}

tasks.withType<Jar> {
    from(rootProject.file("LICENSE"))
}

val authors = gradleProperty("authors")
tasks.processResources {
    val properties = mapOf(
        "version" to project.version,
        "mod_id" to gradleProperty("mod_id"),
        "mod_name" to gradleProperty("mod_name"),
        "mod_description" to gradleProperty("mod_description"),
        "github_url" to gradleProperty("github_url"),
        "license" to gradleProperty("license"),
        "minecraft_version" to versionOf("minecraft"),
        "authors" to authors,
        "authors_json" to formatForJson(authors),
    )

    inputs.properties(properties)

    filesMatching(setOf("fabric.mod.json", "META-INF/neoforge.mods.toml")) {
        expand(properties)
    }
}

// trick to sneak multiple entries into a single placeholder in a JSON file.
// the file must be valid even with placeholders, so we can't just do something like this: [${placeholder}]
// instead, the placeholder is expected to be in a string, like this: ["${placeholder}"]
// this takes a string in the format 'a, b, c' and adds quotes, so the end result will be like this: a", "b", "c
// when filled into the placeholder, you get a valid list: ["a", "b", "c"]
fun formatForJson(entries: String): String {
    return entries.split(", ").joinToString(separator = "\", \"")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    
    repositories {
        val mavenUrl = providers.environmentVariable("local_maven_url");
        if (mavenUrl.isPresent) {
            maven {
                url = uri(mavenUrl.get())
                credentials {
                    username = providers.environmentVariable("local_maven_user").orNull
                    password = providers.environmentVariable("local_maven_token").orNull
                }
            }
        }
    }
}