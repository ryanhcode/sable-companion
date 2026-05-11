val compileOnly: Configuration by configurations.getting
val commonJava: Configuration by configurations.dependencyScope("commonJava")
val commonResources: Configuration by configurations.dependencyScope("commonResources")

val commonPath = ":${rootProject.name}-common"

dependencies {
    compileOnly(project(commonPath))
    commonJava(project(path = commonPath, configuration = "commonMainJava"))
    commonResources(project(path = commonPath, configuration = "commonMainResources"))
}

val resolvableCommonJava: Configuration by configurations.resolvable("resolvableCommonJava") {
    extendsFrom(commonJava)
}

val resolvableCommonResources: Configuration by configurations.resolvable("resolvableCommonResources") {
    extendsFrom(commonResources)
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn(resolvableCommonJava)
    source(resolvableCommonJava)
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(resolvableCommonResources)
    from(resolvableCommonResources)
}

tasks.named<Jar>("sourcesJar") {
    dependsOn(resolvableCommonJava, resolvableCommonResources)
    from(resolvableCommonJava, resolvableCommonResources)
}
