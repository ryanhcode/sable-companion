package dev.ryanhcode.sable.companion.math;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3ic;

import java.util.Iterator;
import java.util.List;

/**
 * An inclusive integer bounding box with JOML interaction.
 *
 * @since 1.0.0
 */
@SuppressWarnings("UnstableApiUsage")
public final class BoundingBox3i implements BoundingBox3ic {

    public static final BoundingBox3ic EMPTY = new BoundingBox3i().setUnchecked(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

    public static Codec<BoundingBox3i> CODEC = Codec.INT.listOf().comapFlatMap((list) -> Util.fixedSize(list, 6).map(
                    (iList) -> new BoundingBox3i(iList.getFirst(),
                            iList.get(1),
                            iList.get(2),
                            iList.get(3),
                            iList.get(4),
                            iList.get(5))),
            (bb) -> List.of(bb.minX,
                    bb.minY,
                    bb.minZ,
                    bb.maxX,
                    bb.maxY,
                    bb.maxZ));

    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3i(final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {
        this.set(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3i(final BlockPos min, final BlockPos max) {
        this.set(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3i(final BoundingBox3ic other) {
        this.set(other);
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3i(final BoundingBox other) {
        this.set(other.minX(), other.minY(), other.minZ(), other.maxX(), other.maxY(), other.maxZ());
    }

    /**
     * Creates a new bounding box with all values set to 0
     */
    public BoundingBox3i() {
        this(0, 0, 0, 0, 0, 0);
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3i(final BoundingBox3d other) {
        this.set(Mth.floor(other.minX), Mth.floor(other.minY), Mth.floor(other.minZ), Mth.floor(other.maxX), Mth.floor(other.maxY), Mth.floor(other.maxZ));
    }

    public static @Nullable BoundingBox3i from(final Iterable<BlockPos> blocks) {
        final Iterator<BlockPos> iterator = blocks.iterator();
        if (!iterator.hasNext()) {
            return null;
        }

        BlockPos pos = iterator.next();
        int minX = pos.getX();
        int minY = pos.getY();
        int minZ = pos.getZ();
        int maxX = minX;
        int maxY = minY;
        int maxZ = minZ;

        while (iterator.hasNext()) {
            pos = iterator.next();
            minX = Math.min(minX, pos.getX());
            minY = Math.min(minY, pos.getY());
            minZ = Math.min(minZ, pos.getZ());
            maxX = Math.max(maxX, pos.getX());
            maxY = Math.max(maxY, pos.getY());
            maxZ = Math.max(maxZ, pos.getZ());
        }

        return new BoundingBox3i(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Sets the bounding box to the given values
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3i set(final BoundingBox3d other) {
        this.set(Mth.floor(other.minX), Mth.floor(other.minY), Mth.floor(other.minZ), Mth.floor(other.maxX), Mth.floor(other.maxY), Mth.floor(other.maxZ));
        return this;
    }

    /**
     * Sets the bounding box to the given values
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3i set(final BoundingBox3ic other) {
        this.set(other.minX(), other.minY(), other.minZ(), other.maxX(), other.maxY(), other.maxZ());
        return this;
    }

    /**
     * Sets the bounding box to the given values
     */
    @Contract(value = "_,_,_,_,_,_->this", mutates = "this")
    public BoundingBox3i set(final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
        this.maxZ = Math.max(minZ, maxZ);
        return this;
    }


    /**
     * Sets the bounding box to the given values.
     * <br>
     * <strong>Does NOT automatically swap mins/maxes if swapped.</strong>
     */
    @Contract(value = "_,_,_,_,_,_->this", mutates = "this")
    public BoundingBox3i setUnchecked(final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        return this;
    }

    /**
     * Sets the bounding box to the given values.
     * <br>
     * <strong>Does NOT automatically swap mins/maxes if swapped.</strong>
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3i setUnchecked(final BoundingBox3ic other) {
        this.minX = other.minX();
        this.minY = other.minY();
        this.minZ = other.minZ();
        this.maxX = other.maxX();
        this.maxY = other.maxY();
        this.maxZ = other.maxZ();
        return this;
    }

    @Contract(value = "_,_,_->this", mutates = "this")
    public BoundingBox3i expand(final int xExpansion, final int yExpansion, final int zExpansion) {
        this.minX -= xExpansion;
        this.minY -= yExpansion;
        this.minZ -= zExpansion;
        this.maxX += xExpansion;
        this.maxY += yExpansion;
        this.maxZ += zExpansion;
        return this;
    }

    /**
     * Expands this box to include the given point
     *
     * @param point the point to include
     * @return the result stored in this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3i expandTo(final Vector3ic point) {
        return this.expandTo(point, this);
    }

    /**
     * Expands this box to include the given point
     *
     * @param x the x value of the point
     * @param y the y value of the point
     * @param z the z value of the point
     * @return the result stored in this
     */
    @Contract(value = "_,_,_->this", mutates = "this")
    public BoundingBox3i expandTo(final int x, final int y, final int z) {
        return this.expandTo(x, y, z, this);
    }

    /**
     * Expands this box to include the given box
     *
     * @param other the box to include
     * @return the result stored in this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3i expandTo(final BoundingBox3ic other) {
        return this.expandTo(other, this);
    }

    /**
     * Translates this box by the given vector
     *
     * @param vec the vector to translate by
     * @return the result stored in this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3i move(final Vector3ic vec) {
        return this.move(vec.x(), vec.y(), vec.z(), this);
    }

    /**
     * Translates this box by the given vector
     *
     * @param x the x value of the vector
     * @param y the y value of the vector
     * @param z the z value of the vector
     * @return the result stored in this
     */
    @Contract(value = "_,_,_->this", mutates = "this")
    public BoundingBox3i move(final int x, final int y, final int z) {
        return this.move(x, y, z, this);
    }

    /**
     * Calculates the bounding box intersect between this box and the specified box.
     *
     * @param box The box to intersect with
     * @return the result stored in this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3i intersect(final BoundingBox3ic box) {
        return this.set(
                Math.max(this.minX(), box.minX()),
                Math.max(this.minY(), box.minY()),
                Math.max(this.minZ(), box.minZ()),
                Math.min(this.maxX(), box.maxX()),
                Math.min(this.maxY(), box.maxY()),
                Math.min(this.maxZ(), box.maxZ()));
    }

    /**
     * @return the minimum x value of this box
     */
    @Override
    public int minX() {
        return this.minX;
    }

    /**
     * @return the minimum y value of this box
     */
    @Override
    public int minY() {
        return this.minY;
    }

    /**
     * @return the minimum z value of this box
     */
    @Override
    public int minZ() {
        return this.minZ;
    }

    /**
     * @return the maximum x value of this box
     */
    @Override
    public int maxX() {
        return this.maxX;
    }

    /**
     * @return the maximum y value of this box
     */
    @Override
    public int maxY() {
        return this.maxY;
    }

    /**
     * @return the maximum z value of this box
     */
    @Override
    public int maxZ() {
        return this.maxZ;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final BoundingBox3ic other)) {
            return false;
        }

        return this.minX() == other.minX() && this.minY() == other.minY() && this.minZ() == other.minZ() && this.maxX() == other.maxX() && this.maxY() == other.maxY() && this.maxZ() == other.maxZ();
    }

    @Override
    public int hashCode() {
        int result = this.minX;
        result = 31 * result + this.minY;
        result = 31 * result + this.minZ;
        result = 31 * result + this.maxX;
        result = 31 * result + this.maxY;
        result = 31 * result + this.maxZ;
        return result;
    }

    @Override
    public String toString() {
        return "BoundingBox3i{" +
                "minX=" + this.minX +
                ", minY=" + this.minY +
                ", minZ=" + this.minZ +
                ", maxX=" + this.maxX +
                ", maxY=" + this.maxY +
                ", maxZ=" + this.maxZ +
                '}';
    }
}
