package dev.ryanhcode.sable.companion.math;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.joml.*;

/**
 * A read-only 3D pose, consisting of a position, rotation, and scale.
 *
 * @since 1.0.0
 */
public sealed interface Pose3dc permits Pose3d {

    /**
     * @return the global position of this pose.
     */
    Vector3dc position();

    /**
     * @return the global orientation of this pose.
     */
    Quaterniondc orientation();

    /**
     * @return the rotation point of this pose.
     */
    Vector3dc rotationPoint();

    /**
     * @return the global scale of this pose.
     */
    Vector3dc scale();

    /**
     * Transform a local position to a global position.
     *
     * @param local the local position to transform
     * @param dest  will hold the result
     * @return dest
     */
    default Vector3d transformPosition(final Vector3dc local, final Vector3d dest) {
        return this.orientation().transform(local.sub(this.rotationPoint(), dest).mul(this.scale())).add(this.position());
    }

    /**
     * Transform a local position to a global position.
     *
     * @param local the local position to transform
     * @return a new vector holding the result
     */
    @Contract("_ -> new")
    default Vec3 transformPosition(final Vec3 local) {
        return JOMLConversion.toMojang(this.transformPosition(JOMLConversion.toJOML(local)));
    }

    /**
     * Transform a global position to a local position.
     *
     * @param global the global position to transform
     * @return a new vector holding the result
     */
    @Contract("_ -> new")
    default Vec3 transformPositionInverse(final Vec3 global) {
        return JOMLConversion.toMojang(this.transformPositionInverse(JOMLConversion.toJOML(global)));
    }

    /**
     * Transform a global position to a local position.
     *
     * @param global the global position to transform
     * @param dest   will hold the result
     * @return dest
     */
    default Vector3d transformPositionInverse(final Vector3dc global, final Vector3d dest) {
        final Vector3dc s = this.scale();
        return this.orientation().transformInverse(global.sub(this.position(), dest))
                .mul( // JOML doesn't have a div method for Vector3dc in this version
                        1.0 / s.x(),
                        1.0 / s.y(),
                        1.0 / s.z()).add(this.rotationPoint());
    }

    /**
     * Transforms a local normal to a global normal.
     * If the scale is non-uniform, the result will not be of the same magnitude.
     *
     * @param local the local normal to transform
     * @param dest  will hold the result
     * @return dest
     */
    default Vector3d transformNormal(final Vector3dc local, final Vector3d dest) {
        return this.orientation().transform(local.mul(this.scale(), dest));
    }

    /**
     * Transforms a global normal to a local normal.
     * If the scale is non-uniform, the result will not be of the same magnitude.
     *
     * @param global the global normal to transform
     * @param dest   will hold the result
     * @return dest
     */
    default Vector3d transformNormalInverse(final Vector3dc global, final Vector3d dest) {
        final Vector3dc s = this.scale();
        return this.orientation().transformInverse(global, dest)
                .mul( // JOML doesn't have a div method for Vector3dc in this version
                        1.0 / s.x(),
                        1.0 / s.y(),
                        1.0 / s.z());
    }

    /**
     * Transforms and mutates a local position to a global position.
     *
     * @param local the local position to transform
     * @return local with the result
     */
    default Vector3d transformPosition(final Vector3d local) {
        return this.transformPosition(local, local);
    }

    /**
     * Transforms and mutates a global position to a local position.
     *
     * @param global the global position to transform
     * @return global with the result
     */
    default Vector3d transformPositionInverse(final Vector3d global) {
        return this.transformPositionInverse(global, global);
    }

    /**
     * Transforms and mutates a local normal to a global normal.
     * If the scale is non-uniform, the result will not be of the same magnitude.
     *
     * @param local the local normal to transform
     * @return local with the result
     */
    default Vector3d transformNormal(final Vector3d local) {
        return this.transformNormal(local, local);
    }

    /**
     * Transforms and mutates a global normal to a local normal.
     * If the scale is non-uniform, the result will not be of the same magnitude.
     *
     * @param global the global normal to transform
     * @return global with the result
     */
    default Vector3d transformNormalInverse(final Vector3d global) {
        return this.transformNormalInverse(global, global);
    }

    /**
     * Transforms a local normal to a global normal.
     * If the scale is non-uniform, the result will not be of the same magnitude.
     *
     * @param local the local normal to transform
     * @return local with the result
     */
    default Vec3 transformNormal(final Vec3 local) {
        return JOMLConversion.toMojang(this.transformNormal(JOMLConversion.toJOML(local)));
    }

    /**
     * Transform a global normal to a local normal.
     * If the scale is non-uniform, the result will not be of the same magnitude.
     *
     * @param global the global normal to transform
     * @return global with the result
     */
    default Vec3 transformNormalInverse(final Vec3 global) {
        return JOMLConversion.toMojang(this.transformNormalInverse(JOMLConversion.toJOML(global)));
    }

    /**
     * Lerps this pose towards the given pose by the given amount.
     *
     * @param pose the pose to lerp towards
     * @param frac the amount to lerp by, 0.0 to 1.0
     * @param dest The destination pose to write into
     * @return The dest pose
     */
    default Pose3d lerp(final Pose3dc pose, final double frac, final Pose3d dest) {
        this.position().lerp(pose.position(), frac, dest.position());
        this.orientation().nlerp(pose.orientation(), frac, dest.orientation());
        this.rotationPoint().lerp(pose.rotationPoint(), frac, dest.rotationPoint());
        this.scale().lerp(pose.scale(), frac, dest.scale());
        return dest;
    }

    /**
     * Bakes this pose into a matrix transform.
     *
     * @param dest will hold the result
     * @return dest
     */
    default Matrix4d bakeIntoMatrix(final Matrix4d dest) {
        return dest.identity()
                .translate(this.position())
                .rotate(this.orientation())
                .scale(this.scale())
                .translate(-this.rotationPoint().x(), -this.rotationPoint().y(), -this.rotationPoint().z());
    }

    /**
     * @param pose3d            the pose to compare to
     * @param distanceTolerance the distance tolerance [m]
     * @param angularTolerance  the angular tolerance [rad]
     * @return if this pose is within the distance tolerance and angular tolerance of another pose
     */
    default boolean withinTolerance(final Pose3d pose3d, final double distanceTolerance, final double angularTolerance) {
        return this.position().distanceSquared(pose3d.position()) <= distanceTolerance * distanceTolerance
                && this.rotationPoint().distanceSquared(pose3d.rotationPoint()) <= distanceTolerance * distanceTolerance
                && this.orientation().div(pose3d.orientation(), new Quaterniond()).angle() <= angularTolerance;
    }
}
