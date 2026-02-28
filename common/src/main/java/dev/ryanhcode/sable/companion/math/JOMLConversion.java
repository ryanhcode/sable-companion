package dev.ryanhcode.sable.companion.math;

import org.joml.Vector3d;

/**
 * Utility for converting between Mojang and JOML math types.
 *
 * @since 1.0.0
 */
public final class JOMLConversion {

    /**
     * A non-changing always zero vector.
     */
    public static final org.joml.Vector3dc ZERO = new Vector3d();

    /**
     * A non-changing always (1.0, 1.0, 1.0) vector.
     */
    public static final org.joml.Vector3dc ONE = new Vector3d(1.0, 1.0, 1.0);

    /**
     * A non-changing always (0.5, 0.5, 0.5) vector.
     */
    public static final org.joml.Vector3dc HALF = new Vector3d(0.5, 0.5, 0.5);

    /**
     * A non-changing always zero vector.
     */
    public static final org.joml.Quaterniondc QUAT_IDENTITY = new org.joml.Quaterniond();

    /**
     * Converts a Mojang Vec3 to a JOML Vector3d.
     *
     * @param vec3 The Mojang Vec3
     * @param dest The JOML vector to store into
     * @return The passed in JOML Vector3d
     */
    public static Vector3d toJOML(final net.minecraft.core.Position vec3, final Vector3d dest) {
        return dest.set(vec3.x(), vec3.y(), vec3.z());
    }

    /**
     * Converts a Mojang Vec3 to a JOML Vector3d.
     *
     * @param vec3 the Mojang Vec3
     * @return A new JOML Vector3d
     */
    public static Vector3d toJOML(final net.minecraft.core.Position vec3) {
        return new Vector3d(vec3.x(), vec3.y(), vec3.z());
    }


    /**
     * Converts a Mojang Vec2 to a JOML Vector2f.
     *
     * @param vec2 The Mojang Vec2
     * @param dest The JOML vector to store into
     * @return The passed in JOML Vector2f
     */
    public static org.joml.Vector2f toJOML(final net.minecraft.world.phys.Vec2 vec2, final org.joml.Vector2f dest) {
        return dest.set(vec2.x, vec2.y);
    }

    /**
     * Converts a Mojang Vec2 to a JOML Vector2f.
     *
     * @param vec2 The Mojang Vec2
     * @return A new JOML Vector2f
     */
    public static org.joml.Vector2f toJOML(final net.minecraft.world.phys.Vec2 vec2) {
        return new org.joml.Vector2f(vec2.x, vec2.y);
    }

    /**
     * Converts a Mojang Vec3I to a JOML Vector3i.
     *
     * @param vec3 the Mojang Vec3i
     * @param dest The JOML vector to store into
     * @return The passed in JOML Vector3d
     */
    public static org.joml.Vector3i toJOML(final net.minecraft.core.Vec3i vec3, final org.joml.Vector3i dest) {
        return dest.set(vec3.getX(), vec3.getY(), vec3.getZ());
    }

    /**
     * Converts a Mojang Vec3i to a JOML Vector3i.
     *
     * @param vec3 the Mojang Vec3i
     * @return A new JOML Vector3d
     */
    public static org.joml.Vector3i toJOML(final net.minecraft.core.Vec3i vec3) {
        return new org.joml.Vector3i(vec3.getX(), vec3.getY(), vec3.getZ());
    }

    /**
     * Copies the coordinates of a Mojang Vec3i exactly.
     *
     * @param toCopy The Mojang Vec3i
     * @param dest   The JOML vector to store into
     * @return The passed in JOML Vector3d
     */
    public static Vector3d atLowerCornerOf(final net.minecraft.core.Vec3i toCopy, final Vector3d dest) {
        return dest.set(toCopy.getX(), toCopy.getY(), toCopy.getZ());
    }

    /**
     * Copies the coordinates of a Mojang Vec3i exactly.
     *
     * @param toCopy The Mojang Vec3i
     * @return A new JOML Vector3d
     */
    public static Vector3d atLowerCornerOf(final net.minecraft.core.Vec3i toCopy) {
        return new Vector3d(toCopy.getX(), toCopy.getY(), toCopy.getZ());
    }

    /**
     * Copies the coordinates of a Mojang Vec3i exactly with offset.
     *
     * @param toCopy  The Mojang Vec3i
     * @param offsetX Offset in the x to add
     * @param offsetY Offset in the y to add
     * @param offsetZ Offset in the z to add
     * @param dest    The JOML vector to store into
     * @return The passed in JOML Vector3d
     */
    public static Vector3d atLowerCornerWithOffset(final net.minecraft.core.Vec3i toCopy, final double offsetX, final double offsetY, final double offsetZ, final Vector3d dest) {
        return dest.set((double) toCopy.getX() + offsetX, (double) toCopy.getY() + offsetY, (double) toCopy.getZ() + offsetZ);
    }

    /**
     * Copies the coordinates of a Mojang Vec3i exactly with offset.
     *
     * @param toCopy  The Mojang Vec3i
     * @param offsetX Offset in the x to add
     * @param offsetY Offset in the y to add
     * @param offsetZ Offset in the z to add
     * @return A new JOML Vector3d
     */
    public static Vector3d atLowerCornerWithOffset(final net.minecraft.core.Vec3i toCopy, final double offsetX, final double offsetY, final double offsetZ) {
        return new Vector3d((double) toCopy.getX() + offsetX, (double) toCopy.getY() + offsetY, (double) toCopy.getZ() + offsetZ);
    }

    /**
     * Copies the coordinates of a Mojang Vec3i and centers them.
     *
     * @param toCopy The Mojang Vec3i
     * @param dest   The JOML vector to store into
     * @return The passed in JOML Vector3d
     */
    public static Vector3d atCenterOf(final net.minecraft.core.Vec3i toCopy, final Vector3d dest) {
        return dest.set((double) toCopy.getX() + 0.5, (double) toCopy.getY() + 0.5, (double) toCopy.getZ() + 0.5);
    }

    /**
     * Copies the coordinates of a Mojang Vec3i and centers them.
     *
     * @param toCopy The Mojang Vec3i
     * @return A new JOML Vector3d
     */
    public static Vector3d atCenterOf(final net.minecraft.core.Vec3i toCopy) {
        return new Vector3d((double) toCopy.getX() + 0.5, (double) toCopy.getY() + 0.5, (double) toCopy.getZ() + 0.5);
    }

    /**
     * Copies the coordinates of a Mojang Vec3i and centers them horizontally (x and z).
     *
     * @param toCopy The Mojang Vec3i
     * @param dest   The JOML vector to store into
     * @return The passed in JOML Vector3d
     */
    public static Vector3d atBottomCenterOf(final net.minecraft.core.Vec3i toCopy, final Vector3d dest) {
        return dest.set((double) toCopy.getX() + 0.5, toCopy.getY(), (double) toCopy.getZ() + 0.5);
    }

    /**
     * Copies the coordinates of a Mojang Vec3i and centers them horizontally (x and z).
     *
     * @param toCopy The Mojang Vec3i
     * @return A new JOML Vector3d
     */
    public static Vector3d atBottomCenterOf(final net.minecraft.core.Vec3i toCopy) {
        return new Vector3d((double) toCopy.getX() + 0.5, toCopy.getY(), (double) toCopy.getZ() + 0.5);
    }

    /**
     * Copies the coordinates of a Mojang Vec3i and centers them horizontally and applies a vertical offset.
     *
     * @param toCopy The Mojang Vec3i
     * @param dest   The JOML vector to store into
     * @return The passed in JOML Vector3d
     */
    public static Vector3d upFromBottomCenterOf(final net.minecraft.core.Vec3i toCopy, final double verticalOffset, final Vector3d dest) {
        return dest.set((double) toCopy.getX() + 0.5, (double) toCopy.getY() + verticalOffset, (double) toCopy.getZ() + 0.5);
    }

    /**
     * Copies the coordinates of a Mojang Vec3i and centers them horizontally and applies a vertical offset.
     *
     * @param toCopy The Mojang Vec3i
     * @return A new JOML Vector3d
     */
    public static Vector3d upFromBottomCenterOf(final net.minecraft.core.Vec3i toCopy, final double verticalOffset) {
        return new Vector3d((double) toCopy.getX() + 0.5, (double) toCopy.getY() + verticalOffset, (double) toCopy.getZ() + 0.5);
    }

    /**
     * Converts a JOML Vector3d to a Mojang Vec3.
     *
     * @param vec3d the JOML Vector3d
     * @return the Mojang Vec3
     */
    public static net.minecraft.world.phys.Vec3 toMojang(final org.joml.Vector3dc vec3d) {
        return new net.minecraft.world.phys.Vec3(vec3d.x(), vec3d.y(), vec3d.z());
    }

    /**
     * Converts a JOML Vector2f to a Mojang Vec2.
     *
     * @param vec2f the JOML Vector2f
     * @return the Mojang Vec2
     */
    public static net.minecraft.world.phys.Vec2 toMojang(final org.joml.Vector2fc vec2f) {
        return new net.minecraft.world.phys.Vec2(vec2f.x(), vec2f.y());
    }

    /**
     * Converts a JOML Vector3i to a Mojang Vec3i.
     *
     * @param vec3i the JOML Vector3d
     * @return the Mojang Vec3
     */
    public static net.minecraft.core.Vec3i toMojang(final org.joml.Vector3ic vec3i) {
        return new net.minecraft.core.Vec3i(vec3i.x(), vec3i.y(), vec3i.z());
    }

    /**
     * Calculates the center of the specified Mojang AABB.
     *
     * @param aabb The Mojang AABB
     * @return A new JOML Vector3d
     */
    public static Vector3d getAABBCenter(final net.minecraft.world.phys.AABB aabb) {
        return new Vector3d(
                (aabb.minX + aabb.maxX) / 2.0,
                (aabb.minY + aabb.maxY) / 2.0,
                (aabb.minZ + aabb.maxZ) / 2.0);
    }

    /**
     * Calculates the center of the specified Mojang AABB.
     *
     * @param aabb The Mojang AABB
     * @param dest The JOML vector to store into
     * @return The passed in JOML Vector3d
     */
    public static Vector3d getAABBCenter(final net.minecraft.world.phys.AABB aabb, final Vector3d dest) {
        return dest.set(
                (aabb.minX + aabb.maxX) / 2.0,
                (aabb.minY + aabb.maxY) / 2.0,
                (aabb.minZ + aabb.maxZ) / 2.0);
    }
}
