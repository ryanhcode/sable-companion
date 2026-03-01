# Sable Companion

[![Sable Companion 1.21.x](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.ryanhcode.dev%2Freleases%2Fdev%2Fryanhcode%2Fsable-companion%2Fsable-companion-1.21.1%2Fmaven-metadata.xml&label=Sable%20Companion%201.21.x)]([-1.21.1/](https://maven.ryanhcode.dev/releases/dev/ryanhcode/sable-companion/sable-companion-1.21.1/))

Companion is an extremely lightweight library for making mods compatible with [Sable](https://github.com/ryanhcode/sable).
It is intended to be included or JiJ'd into mods, and contains a safe default implementation of all methods which is overridden by Sable when installed.

## Getting Started

Copy the following segments into your `build.gradle` file depending on the platform:

### NeoForge

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

## Compatibility Advice

### Working with Positions

Positions inside of sub-level plots are stored with extreme values inside the plot grid.
Companion has utilities to work with positions in plots: 

```java
Level level = ...;

// To find the loaded sub-level containing a given position in its plot, if present:
SubLevelAccess subLevelAccess = SableCompanion.INSTANCE.getContaining(level, pos);

// To check if a given position is contained in the plot grid:
boolean isInPlotGrid = SableCompanion.INSTANCE.isInPlotGrid(level, pos);
```

### Projecting Positions

With a `SubLevelAccess`, the logical (current), last, and render "pose" can be obtained to transform positions in and out of its plot.

```java
// Example: Transform a position out of a sub-level into "global" space
Vec3 position = ...;
SubLevelAccess subLevelAccess = SableCompanion.INSTANCE.getContaining(level, pos);

if (subLevelAccess != null) {
    Pose3dc pose = subLevelAccess.logicalPose();

    // Transform the position to global space
    position = pose.transformPosition(position);
}

// Companion has a compact utility for the above that projects a position out of a sub-level (if it is one)
position = SableCompanion.INSTANCE.projectOutOfSubLevel(level, position);

```

### Distance Checks

In order to get the *global* distance between two points (either of which can be within a sub-level), companion has a utility.

```java
Level level = ...;
Vec3 a = ...;
Vec3 b = ...;

// INCORRECT - distance will be extreme if one or both of the positions are in the plot
double incorrectDistanceSquared = a.distanceToSqr(b);

// CORRECT - distance will be computed in global space
double distanceSquared = SableCompanion.INSTANCE.distanceSquaredWithSubLevels(level, a, b);
```

Some distance checks are already safe, and do not require the use of companion.
Sable already corrects the following:

```java
Entity#distanceToSqr(double x, double y, double z);
Entity#distanceToSqr(Vec3 vec3);
Entity#distanceToSqr(Entity entity);
Entity#distanceTo(Entity entity);
```

