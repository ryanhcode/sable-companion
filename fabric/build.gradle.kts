plugins {
    alias(libs.plugins.loom.remap)
    alias(libs.plugins.configure.base)
    alias(libs.plugins.consume.common)
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
}