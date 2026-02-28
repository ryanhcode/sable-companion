package dev.ryanhcode.sable.companion.math;

import com.mojang.serialization.Codec;
import dev.ryanhcode.sable.companion.impl.SableCompanionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3dc;

import java.util.List;

/**
 * A bounding box with JOML interaction.
 *
 * @since 1.0.0
 */
@SuppressWarnings("UnstableApiUsage")
public final class BoundingBox3d implements BoundingBox3dc {

    public static final BoundingBox3d EMPTY = new BoundingBox3d(0, 0, 0, 0, 0, 0);

    public static Codec<BoundingBox3d> CODEC = Codec.DOUBLE.listOf().comapFlatMap((list) -> SableCompanionUtil.fixedSize(list, 6).map(
                    (iList) -> new BoundingBox3d(iList.getFirst(),
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

    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3d(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        this.set(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3d(final BoundingBox3dc other) {
        this.set(other);
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3d(final AABB other) {
        this.set(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3d(final BoundingBox other) {
        this.set(other.minX(), other.minY(), other.minZ(), other.maxX(), other.maxY(), other.maxZ());
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3d(final BlockPos pos) {
        this.set(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3d(final BoundingBox3ic other) {
        this.set(other.minX(), other.minY(), other.minZ(), other.maxX() + 1, other.maxY() + 1, other.maxZ() + 1);
    }

    /**
     * Creates a new bounding box with the given values
     */
    public BoundingBox3d(final Vec3 from, final Vec3 to) {
        this.set(from.x, from.y, from.z, to.x, to.y, to.z);
    }

    /**
     * Default constructor for an all-zero bounding box
     */
    public BoundingBox3d() {
        this.set(0, 0, 0, 0, 0, 0);
    }

    /**
     * Sets the bounding box to the given values
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d set(final BoundingBox3dc other) {
        this.set(other.minX(), other.minY(), other.minZ(), other.maxX(), other.maxY(), other.maxZ());
        return this;
    }

    /**
     * Sets the bounding box to the given values
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d set(final AABB other) {
        this.set(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
        return this;
    }

    /**
     * Sets the bounding box to the given values.
     * Automatically swaps the values to ensure min values are less than max values.
     */
    @Contract(value = "_,_,_,_,_,_->this", mutates = "this")
    public BoundingBox3d set(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
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
    public BoundingBox3d setUnchecked(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
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
    public BoundingBox3d setUnchecked(final BoundingBox3dc other) {
        this.minX = other.minX();
        this.minY = other.minY();
        this.minZ = other.minZ();
        this.maxX = other.maxX();
        this.maxY = other.maxY();
        this.maxZ = other.maxZ();
        return this;
    }

    /**
     * Expands this box to include the given point
     *
     * @param point the point to include
     * @return the result stored in dest
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d expandTo(final Vector3dc point) {
        return this.expandTo(point.x(), point.y(), point.z());
    }

    /**
     * Expands this box to include the given point
     *
     * @param x the x value of the point
     * @param y the y value of the point
     * @param z the z value of the point
     * @return this
     */
    @Contract(value = "_,_,_->this", mutates = "this")
    public BoundingBox3d expandTo(final double x, final double y, final double z) {
        return this.expandTo(x, y, z, this);
    }

    /**
     * Expands this box to include the given box
     *
     * @param other the box to include
     * @return this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d expandTo(final BoundingBox3dc other) {
        return this.expandTo(other, this);
    }

    /**
     * Expands this box by the given amount on all sides
     *
     * @param amount the amount to expand by
     * @return this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d expand(final double amount) {
        return this.expand(amount, amount, amount);
    }

    /**
     * Expands this box by the given amount on all sides
     *
     * @param amountX the amount to expand by in the x
     * @param amountY the amount to expand by in the y
     * @param amountZ the amount to expand by in the z
     * @return this
     */
    @Contract(value = "_,_,_->this", mutates = "this")
    public BoundingBox3d expand(final double amountX, final double amountY, final double amountZ) {
        return this.expand(amountX, amountY, amountZ, this);
    }

    /**
     * Moves this box by the given amount on all sides
     *
     * @param amountX The amount to move by in the x
     * @param amountY The amount to move by in the y
     * @param amountZ The amount to move by in the z
     * @return this
     */
    @Contract(value = "_,_,_->this", mutates = "this")
    public BoundingBox3d move(final double amountX, final double amountY, final double amountZ) {
        return this.move(amountX, amountY, amountZ, this);
    }

    /**
     * Calculates the bounding box intersect between this box and the specified box.
     *
     * @param box The box to intersect with
     * @return this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d intersect(final BoundingBox3dc box) {
        return this.intersect(box, this);
    }

    /**
     * Transforms a bounding box by the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in this bounding box.
     *
     * @param pose the pose to transform by
     * @return this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d transform(final Pose3dc pose) {
        return this.transform(pose, this);
    }

    /**
     * Transforms a bounding box by the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in this bounding box.
     *
     * @param pose the pose to transform by
     * @return this
     */
    @Contract(value = "_,_->this", mutates = "this")
    public BoundingBox3d transform(final Pose3dc pose, final Matrix4d bakedMatrix) {
        return this.transform(pose, bakedMatrix, this);
    }

    /**
     * Transforms a bounding box by the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in this bounding box.
     *
     * @param mpose the pose to transform by
     * @return this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d transform(final Matrix4dc mpose) {
        return this.transform(mpose, this);
    }

    /**
     * Transforms a bounding box by the inverse of the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in this bounding box.
     *
     * @param pose the pose to transform by
     * @return this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d transformInverse(final Pose3dc pose) {
        return this.transformInverse(pose, this);
    }

    /**
     * Transforms a bounding box by the inverse of the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in this bounding box.
     *
     * @param pose the pose to transform by
     * @return this
     */
    @Contract(value = "_,_->this", mutates = "this")
    public BoundingBox3d transformInverse(final Pose3dc pose, final Matrix4d bakedMatrix) {
        return this.transformInverse(pose, bakedMatrix, this);
    }

    /**
     * Transforms a bounding box by the inverse of the given pose, picking the maximum bounds around the transformed corners
     * and storing the result in this bounding box.
     *
     * @param mpose the pose to transform by
     * @return this
     */
    @Contract(value = "_->this", mutates = "this")
    public BoundingBox3d transformInverse(final Matrix4dc mpose) {
        return this.transformInverse(mpose, this);
    }

    /**
     * @return the minimum x value of this box
     */
    @Override
    public double minX() {
        return this.minX;
    }

    /**
     * @return the minimum y value of this box
     */
    @Override
    public double minY() {
        return this.minY;
    }

    /**
     * @return the minimum z value of this box
     */
    @Override
    public double minZ() {
        return this.minZ;
    }

    /**
     * @return the maximum x value of this box
     */
    @Override
    public double maxX() {
        return this.maxX;
    }

    /**
     * @return the maximum y value of this box
     */
    @Override
    public double maxY() {
        return this.maxY;
    }

    /**
     * @return the maximum z value of this box
     */
    @Override
    public double maxZ() {
        return this.maxZ;
    }
}
