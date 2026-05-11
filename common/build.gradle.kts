plugins {
    alias(libs.plugins.loom.remap)
    alias(libs.plugins.configure.base)
    alias(libs.plugins.provide.common)
}

loom.productionNamespace = "named"

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
}