# Sable Companion

A lightweight library for making mods compatible with Sable

## Getting Started

Copy the following segments into your `build.gradle` file depending on the platform.

### Neoforge

<details>
  <summary>Click to expand</summary>

```groovy
reositories {
    maven {
        name = "RyanHCode Maven"
        url = "https://maven.ryanhcode.dev/releases"
    }
}

dependencies {
    jarJar(api("dev.ryanhcode.sable-companion:sable-companion-${project.minecraft_version}:[${project.sable_companion_version},)")) {
        version {
            prefer project.sable_companion_version
        }
    }
}
```

</details>

### Fabric

<details>
  <summary>Click to expand</summary>

```groovy
reositories {
    maven {
        name = "RyanHCode Maven"
        url = "https://maven.ryanhcode.dev/releases"
    }
}

dependencies {
    include(modApi("dev.ryanhcode.sable-companion:sable-companion-${project.minecraft_version}:${project.sable_companion_version}"))
}
```

</details>

### Common

<details>
  <summary>Click to expand</summary>

```groovy
reositories {
    maven {
        name = "RyanHCode Maven"
        url = "https://maven.ryanhcode.dev/releases"
    }
}

dependencies {
    api "dev.ryanhcode.sable-companion:sable-companion-${project.minecraft_version}:${project.sable_companion_version}"
}
```

</details>