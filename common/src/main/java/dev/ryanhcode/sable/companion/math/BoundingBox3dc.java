package dev.ryanhcode.sable.companion.math;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Contract;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

/**
 * A bounding box with JOML interaction.
 *
 * @since 1.0.0
 */
@SuppressWarnings("UnstableApiUsage")
public sealed interface BoundingBox3dc permits BoundingBox3d {

    /**
     * @return if this box intersects with the given other box
     */
    default boolean intersects(final BoundingBox3dc other) {
        return this.intersects(other.minX(), other.minY(), other.minZ(), other.maxX(), other.maxY(), other.maxZ());
    }

    /**
     * @return if this box intersects with the given other box
     */
    default boolean intersects(final AABB other) {
        return this.intersects(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
    }

    /**
     * @return if this box intersects with the given other box
     */
    default boolean intersects(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        return this.maxX() >= minX && this.maxY() >= minY && this.maxZ() >= minZ && this.minX() <= maxX && this.minY() <= maxY && this.minZ() <= maxZ;
    }

    /**
     * @return if this box contains the given point
     */
    default boolean contains(final Vector3dc point) {
        return this.contains(point.x(), point.y(), point.z());
    }

    /**
     * @return if this box contains the given point
     */
    default boolean contains(final double x, final double y, final double z) {
        return x >= this.minX() && x <= this.maxX() && y >= this.minY() && y <= this.maxY() && z >= this.minZ() && z <= this.maxZ();
    }

    /**
     * @return the minimum x value of this box
     */
    double minX();

    /**
     * @return the minimum y value of this box
     */
    double minY();

    /**
     * @return the minimum z value of this box
     */
    double minZ();

    /**
     * @return the maximum x value of this box
     */
    double maxX();

    /**
     * @return the maximum y value of this box
     */
    double maxY();

    /**
     * @return the maximum z value of this box
     */
    double maxZ();

    /**
     * Expands this box to include the given point
     *
     * @param point the point to include
     * @return the result stored in dest
     */
    default BoundingBox3d expandTo(final Vector3dc point, final BoundingBox3d dest) {
        return this.expandTo(point.x(), point.y(), point.z(), dest);
    }

    /**
     * Expands this box to include the given point
     *
     * @param x the x value of the point
     * @param y the y value of the point
     * @param z the z value of the point
     * @return the result stored in dest
     */
    default BoundingBox3d expandTo(final double x, final double y, final double z, final BoundingBox3d dest) {
        dest.setUnchecked(this);
        dest.maxX = Math.max(dest.maxX, x);
        dest.maxY = Math.max(dest.maxY, y);
        dest.maxZ = Math.max(dest.maxZ, z);
        dest.minX = Math.min(dest.minX, x);
        dest.minY = Math.min(dest.minY, y);
        dest.minZ = Math.min(dest.minZ, z);
        return dest;
    }

    /**
     * Expands this box to include the given box
     *
     * @param other the box to include
     * @return the result stored in dest
     */
    default BoundingBox3d expandTo(final BoundingBox3dc other, final BoundingBox3d dest) {
        dest.setUnchecked(this);
        dest.maxX = Math.max(dest.maxX, other.maxX());
        dest.maxY = Math.max(dest.maxY, other.maxY());
        dest.maxZ = Math.max(dest.maxZ, other.maxZ());
        dest.minX = Math.min(dest.minX, other.minX());
        dest.minY = Math.min(dest.minY, other.minY());
        dest.minZ = Math.min(dest.minZ, other.minZ());
        return dest;
    }

    /**
     * Expands this box by the given amount on all sides
     *
     * @param amount the amount to expand by
     * @param dest   the destination bounding box
     * @return the result stored in dest
     */
    default BoundingBox3d expand(final double amount, final BoundingBox3d dest) {
        return dest.setUnchecked(
                this.minX() - amount,
                this.minY() - amount,
                this.minZ() - amount,
                this.maxX() + amount,
                this.maxY() + amount,
                this.maxZ() + amount);
    }

    /**
     * Expands this box by the given amount on all sides
     *
     * @param amountX the amount to expand by in the x
     * @param amountY the amount to expand by in the y
     * @param amountZ the amount to expand by in the z
     * @param dest    the destination bounding box
     * @return the result stored in dest
     */
    default BoundingBox3d expand(final double amountX, final double amountY, final double amountZ, final BoundingBox3d dest) {
        return dest.setUnchecked(
                this.minX() - amountX,
                this.minY() - amountY,
                this.minZ() - amountZ,
                this.maxX() + amountX,
                this.maxY() + amountY,
                this.maxZ() + amountZ);
    }

    /**
     * Moves this box by the given amount on all sides
     *
     * @param amountX The amount to move by in the x
     * @param amountY The amount to move by in the y
     * @param amountZ The amount to move by in the z
     * @param dest    The destination bounding box
     * @return The result stored in dest
     */
    default BoundingBox3d move(final double amountX, final double amountY, final double amountZ, final BoundingBox3d dest) {
        return dest.setUnchecked(
                this.minX() + amountX,
                this.minY() + amountY,
                this.minZ() + amountZ,
                this.maxX() + amountX,
                this.maxY() + amountY,
                this.maxZ() + amountZ);
    }

    /**
     * Calculates the bounding box intersect between this box and the specified box.
     *
     * @param box  The box to intersect with
     * @param dest The destination bounding box
     * @return The result stored in dest
     */
    default BoundingBox3d intersect(final BoundingBox3dc box, final BoundingBox3d dest) {
        return dest.setUnchecked(
                Math.max(this.minX(), box.minX()),
                Math.max(this.minY(), box.minY()),
                Math.max(this.minZ(), box.minZ()),
                Math.min(this.maxX(), box.maxX()),
                Math.min(this.maxY(), box.maxY()),
                Math.min(this.maxZ(), box.maxZ()));
    }

    /**
     * Transforms a bounding box by the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in dest
     *
     * @param pose the pose to transform by
     * @param dest the destination bounding box
     * @return the destination bounding box
     */
    default BoundingBox3d transform(final Pose3dc pose, final BoundingBox3d dest) {
        return this.transform(pose.bakeIntoMatrix(new Matrix4d()), dest);
    }

    /**
     * Transforms a bounding box by the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in dest
     *
     * @param pose the pose to transform by
     * @param dest the destination bounding box
     * @return the destination bounding box
     */
    default BoundingBox3d transform(final Pose3dc pose, final Matrix4d bakedMatrix, final BoundingBox3d dest) {
        return this.transform(pose.bakeIntoMatrix(bakedMatrix), dest);
    }

    /**
     * Transforms a bounding box by the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in dest
     *
     * @param mpose the pose to transform by
     * @param dest  the destination bounding box
     * @return the destination bounding box
     */
    default BoundingBox3d transform(final Matrix4dc mpose, final BoundingBox3d dest) {
        final double minX = this.minX(), minY = this.minY(), minZ = this.minZ();
        final double maxX = this.maxX(), maxY = this.maxY(), maxZ = this.maxZ();

        dest.setUnchecked(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        final Vector3d corner = new Vector3d();

        for (int i = 0; i <= 0b111; i++) {
            corner.set(
                    (i & 0b001) == 0 ? minX : maxX,
                    (i & 0b010) == 0 ? minY : maxY,
                    (i & 0b100) == 0 ? minZ : maxZ
            );
            dest.expandTo(mpose.transformPosition(corner), dest);
        }

        return dest;
    }

    /**
     * Transforms a bounding box by the inverse of the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in dest
     *
     * @param pose the pose to transform by
     * @param dest the destination bounding box
     * @return the destination bounding box
     */
    default BoundingBox3d transformInverse(final Pose3dc pose, final BoundingBox3d dest) {
        return this.transformInverse(pose.bakeIntoMatrix(new Matrix4d()).invertAffine(), dest);
    }

    /**
     * Transforms a bounding box by the inverse of the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in dest
     *
     * @param pose the pose to transform by
     * @param dest the destination bounding box
     * @return the destination bounding box
     */
    default BoundingBox3d transformInverse(final Pose3dc pose, final Matrix4d bakedMatrix, final BoundingBox3d dest) {
        return this.transformInverse(pose.bakeIntoMatrix(bakedMatrix).invertAffine(), dest);
    }

    /**
     * Transforms a bounding box by the inverse of the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in dest
     *
     * @param mpose the pose to transform by
     * @param dest  the destination bounding box
     * @return the destination bounding box
     */
    default BoundingBox3d transformInverse(final Matrix4dc mpose, final BoundingBox3d dest) {
        final double minX = this.minX(), minY = this.minY(), minZ = this.minZ();
        final double maxX = this.maxX(), maxY = this.maxY(), maxZ = this.maxZ();

        dest.setUnchecked(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        final Vector3d corner = new Vector3d();

        for (int i = 0; i <= 0b111; i++) {
            corner.set(
                    (i & 0b001) == 0 ? minX : maxX,
                    (i & 0b010) == 0 ? minY : maxY,
                    (i & 0b100) == 0 ? minZ : maxZ
            );
            dest.expandTo(mpose.transformPosition(corner), dest);
        }

        return dest;
    }

    /**
     * @return the center of this box
     */
    default Vector3d center() {
        return this.center(new Vector3d());
    }

    /**
     * @return the center of this box stored in dest
     */
    default Vector3d center(final Vector3d dest) {
        return dest.set((this.minX() + this.maxX()) / 2, (this.minY() + this.maxY()) / 2, (this.minZ() + this.maxZ()) / 2);
    }

    /**
     * @return the center of this box
     */
    default Vector3d size() {
        return this.size(new Vector3d());
    }

    /**
     * @return the side length vector of this box stored in dest
     */
    default Vector3d size(final Vector3d dest) {
        return dest.set(this.maxX() - this.minX(), this.maxY() - this.minY(), this.maxZ() - this.minZ());
    }

    /**
     * @return The chunk sections this bounding box intersects
     */
    @Contract(value = "->new", pure = true)
    default BoundingBox3i chunkBoundsFrom() {
        return this.chunkBoundsFrom(new BoundingBox3i());
    }

    /**
     * @return The chunk sections this bounding box intersects
     */
    @Contract(value = "_->param1", mutates = "param1")
    default BoundingBox3i chunkBoundsFrom(final BoundingBox3i dest) {
        return dest.set(
                Mth.floor(this.minX()) >> 4,
                Mth.floor(this.minY()) >> 4,
                Mth.floor(this.minZ()) >> 4,
                Mth.floor(this.maxX()) >> 4,
                Mth.floor(this.maxY()) >> 4,
                Mth.floor(this.maxZ()) >> 4
        );
    }

    /**
     * @return the encompassing X range
     * @since 1.1.0
     */
    @Contract(pure = true)
    default double width() {
        return this.maxX() - this.minX() + 1;
    }

    /**
     * @return the encompassing Y range
     * @since 1.1.0
     */
    @Contract(pure = true)
    default double height() {
        return this.maxY() - this.minY() + 1;
    }

    /**
     * @return the encompassing Z range
     * @since 1.1.0
     */
    @Contract(pure = true)
    default double length() {
        return this.maxZ() - this.minZ() + 1;
    }

    /**
     * @return the volume of this box
     */
    @Contract(pure = true)
    default double volume() {
        return (this.maxX() - this.minX()) * (this.maxY() - this.minY()) * (this.maxZ() - this.minZ());
    }

    /**
     * @return a new Mojang bounding box with the same values as this box
     */
    @Contract(value = "->new", pure = true)
    default AABB toMojang() {
        return new AABB(this.minX(), this.minY(), this.minZ(), this.maxX(), this.maxY(), this.maxZ());
    }
}
