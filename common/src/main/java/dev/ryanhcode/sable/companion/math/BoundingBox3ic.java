package dev.ryanhcode.sable.companion.math;

import net.minecraft.core.SectionPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Contract;
import org.joml.Vector3dc;
import org.joml.Vector3i;
import org.joml.Vector3ic;

/**
 * An inclusive integer bounding box with JOML interaction.
 *
 * @since 1.0.0
 */
@SuppressWarnings("UnstableApiUsage")
public sealed interface BoundingBox3ic permits BoundingBox3i {

    /**
     * @return if this box intersects with the given other box
     */
    @Contract(pure = true)
    default boolean intersects(final BoundingBox3ic other) {
        return this.intersects(other.minX(), other.minY(), other.minZ(), other.maxX(), other.maxY(), other.maxZ());
    }

    /**
     * @return if this box intersects with the given other box
     */
    @Contract(pure = true)
    default boolean intersects(final BoundingBox other) {
        return this.intersects(other.minX(), other.minY(), other.minZ(), other.maxX(), other.maxY(), other.maxZ());
    }

    /**
     * @return if this box intersects with the given other box
     */
    @Contract(pure = true)
    default boolean intersects(final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {
        return this.maxX() >= minX && this.maxY() >= minY && this.maxZ() >= minZ && this.minX() <= maxX && this.minY() <= maxY && this.minZ() <= maxZ;
    }

    /**
     * @return if this box contains the given point
     */
    @Contract(pure = true)
    default boolean contains(final Vector3ic point) {
        return this.contains(point.x(), point.y(), point.z());
    }

    /**
     * @return if this box contains the given point
     */
    @Contract(pure = true)
    default boolean contains(final int x, final int y, final int z) {
        return x >= this.minX() && x <= this.maxX() && y >= this.minY() && y <= this.maxY() && z >= this.minZ() && z <= this.maxZ();
    }

    /**
     * @return if this box contains the given point
     */
    @Contract(pure = true)
    default boolean contains(final Vector3dc other) {
        return other.x() >= this.minX() && other.x() <= this.maxX() + 1 && other.y() >= this.minY() && other.y() <= this.maxY() + 1 && other.z() >= this.minZ() && other.z() <= this.maxZ() + 1;
    }

    /**
     * @return the minimum x value of this box
     */
    @Contract(pure = true)
    int minX();

    /**
     * @return the minimum y value of this box
     */
    @Contract(pure = true)
    int minY();

    /**
     * @return the minimum z value of this box
     */
    @Contract(pure = true)
    int minZ();

    /**
     * @return the maximum x value of this box
     */
    @Contract(pure = true)
    int maxX();

    /**
     * @return the maximum y value of this box
     */
    @Contract(pure = true)
    int maxY();

    /**
     * @return the maximum z value of this box
     */
    @Contract(pure = true)
    int maxZ();

    /**
     * Expands this box to include the given point
     *
     * @param point the point to include
     * @return the result stored in dest
     */
    @Contract(value = "_,_->param2", mutates = "param2")
    default BoundingBox3i expandTo(final Vector3ic point, final BoundingBox3i dest) {
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
    @Contract(value = "_,_,_,_->param4", mutates = "param4")
    default BoundingBox3i expandTo(final int x, final int y, final int z, final BoundingBox3i dest) {
        dest.set(this);
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
    @Contract(value = "_,_->param2", mutates = "param2")
    default BoundingBox3i expandTo(final BoundingBox3ic other, final BoundingBox3i dest) {
        dest.set(this);
        dest.maxX = Math.max(dest.maxX, other.maxX());
        dest.maxY = Math.max(dest.maxY, other.maxY());
        dest.maxZ = Math.max(dest.maxZ, other.maxZ());
        dest.minX = Math.min(dest.minX, other.minX());
        dest.minY = Math.min(dest.minY, other.minY());
        dest.minZ = Math.min(dest.minZ, other.minZ());
        return dest;
    }

    /**
     * Translates this box by the given vector
     *
     * @param vec the vector to translate by
     * @return the result stored in dest
     */
    @Contract(value = "_,_->param2", mutates = "param2")
    default BoundingBox3i move(final Vector3ic vec, final BoundingBox3i dest) {
        return this.move(vec.x(), vec.y(), vec.z(), dest);
    }

    /**
     * Translates this box by the given vector
     *
     * @param x the x value of the vector
     * @param y the y value of the vector
     * @param z the z value of the vector
     * @return the result stored in dest
     */
    @Contract(value = "_,_,_,_->param4", mutates = "param4")
    default BoundingBox3i move(final int x, final int y, final int z, final BoundingBox3i dest) {
        dest.set(this);
        dest.minX += x;
        dest.minY += y;
        dest.minZ += z;
        dest.maxX += x;
        dest.maxY += y;
        dest.maxZ += z;
        return dest;
    }

    /**
     * Calculates the bounding box intersect between this box and the specified box.
     *
     * @param box  The box to intersect with
     * @param dest The destination bounding box
     * @return The result stored in dest
     */
    @Contract(value = "_,_->param2", mutates = "param2")
    default BoundingBox3i intersect(final BoundingBox3ic box, final BoundingBox3i dest) {
        dest.setUnchecked(
                Math.max(this.minX(), box.minX()),
                Math.max(this.minY(), box.minY()),
                Math.max(this.minZ(), box.minZ()),
                Math.min(this.maxX(), box.maxX()),
                Math.min(this.maxY(), box.maxY()),
                Math.min(this.maxZ(), box.maxZ()));
        return dest;
    }

    /**
     * @return the center of this box stored in dest
     */
    @Contract(value = "_->param1", mutates = "param1")
    default Vector3i center(final Vector3i dest) {
        return dest.set((this.minX() + this.maxX()) / 2, (this.minY() + this.maxY()) / 2, (this.minZ() + this.maxZ()) / 2);
    }

    /**
     * @return the side length vector of this box stored in dest
     */
    @Contract(value = "_->param1", mutates = "param1")
    default Vector3i size(final Vector3i dest) {
        return dest.set(this.maxX() - this.minX(), this.maxY() - this.minY(), this.maxZ() - this.minZ());
    }

    /**
     * @return the encompassing X range
     */
    @Contract(pure = true)
    default int width() {
        return this.maxX() - this.minX() + 1;
    }

    /**
     * @return the encompassing Y range
     */
    @Contract(pure = true)
    default int height() {
        return this.maxY() - this.minY() + 1;
    }

    /**
     * @return the encompassing Z range
     */
    @Contract(pure = true)
    default int length() {
        return this.maxZ() - this.minZ() + 1;
    }

    /**
     * @return the volume of this box
     */
    @Contract(pure = true)
    default int volume() {
        return (this.maxX() - this.minX() + 1) * (this.maxY() - this.minY() + 1) * (this.maxZ() - this.minZ() + 1);
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
                this.minX() >> SectionPos.SECTION_BITS,
                this.minY() >> SectionPos.SECTION_BITS,
                this.minZ() >> SectionPos.SECTION_BITS,
                this.maxX() >> SectionPos.SECTION_BITS,
                this.maxY() >> SectionPos.SECTION_BITS,
                this.maxZ() >> SectionPos.SECTION_BITS
        );
    }

    /**
     * @return A new Minecraft {@link AABB} with the same bounds as this box
     */
    @Contract(value = "->new", pure = true)
    default AABB toAABB() {
        return new AABB(
                this.minX(), this.minY(), this.minZ(),
                this.maxX() + 1, this.maxY() + 1, this.maxZ() + 1
        );
    }

    /**
     * @return A new Minecraft {@link BoundingBox} with the same bounds as this box
     */
    @Contract(value = "->new", pure = true)
    default BoundingBox toMojang() {
        return new BoundingBox(
                this.minX(), this.minY(), this.minZ(),
                this.maxX(), this.maxY(), this.maxZ()
        );
    }
}
