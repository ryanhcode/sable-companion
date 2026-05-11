configurations {
    consumable("commonMainJava")
    consumable("commonMainResources")
}

val sourceSets = extensions.getByType<SourceSetContainer>()

artifacts {
    sourceSets["main"].run {
        java.sourceDirectories.forEach { add("commonMainJava", it) }
        resources.sourceDirectories.forEach { add("commonMainResources", it) }
    }
}
