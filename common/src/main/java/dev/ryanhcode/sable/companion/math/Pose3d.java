package dev.ryanhcode.sable.companion.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import org.jetbrains.annotations.Contract;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import java.text.NumberFormat;
import java.util.List;

/**
 * A read-write 3D pose, consisting of a position, rotation, and scale.
 *
 * @since 1.0.0
 */
@SuppressWarnings("UnstableApiUsage")
public final class Pose3d implements Pose3dc {

    private static final Codec<Vector3d> VECTOR_3D_CODEC = Codec.DOUBLE.listOf()
            .comapFlatMap(l -> Util.fixedSize(l, 3).map(
                            list -> new Vector3d(list.getFirst(), list.get(1), list.get(2))),
                    vec -> List.of(vec.x, vec.y, vec.z));

    private static final Codec<Quaterniond> QUATERNIOND_CODEC = Codec.DOUBLE.listOf()
            .comapFlatMap(l -> Util.fixedSize(l, 4).map(
                            list -> new Quaterniond(list.getFirst(), list.get(1), list.get(2), list.get(3))),
                    quat -> List.of(quat.x, quat.y, quat.z, quat.w));

    public static Codec<Pose3d> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            VECTOR_3D_CODEC.fieldOf("position").forGetter(Pose3d::position),
            QUATERNIOND_CODEC.fieldOf("orientation").forGetter(Pose3d::orientation),
            VECTOR_3D_CODEC.fieldOf("rotation_point").forGetter(Pose3d::rotationPoint),
            VECTOR_3D_CODEC.fieldOf("scale").forGetter(Pose3d::scale)
    ).apply(instance, Pose3d::new));

    private final Vector3d position;
    private final Quaterniond orientation;
    private final Vector3d rotationPoint;
    private final Vector3d scale;

    /**
     * Constructs a new pose with the given position, orientation, and scale.
     *
     * @param position    the global position of the pose
     * @param orientation the global orientation of the pose
     * @param scale       the global scale of the pose
     */
    public Pose3d(final Vector3d position, final Quaterniond orientation, final Vector3d rotationPoint, final Vector3d scale) {
        this.position = position;
        this.orientation = orientation;
        this.rotationPoint = rotationPoint;
        this.scale = scale;
    }

    /**
     * Constructs a new pose with the identity position, orientation, and scale.
     */
    public Pose3d() {
        this.position = new Vector3d();
        this.orientation = new Quaterniond();
        this.rotationPoint = new Vector3d();
        this.scale = new Vector3d(1.0);
    }

    /**
     * Constructs a new pose, copying the values from the given pose.
     */
    public Pose3d(final Pose3dc pose) {
        this.position = new Vector3d(pose.position());
        this.orientation = new Quaterniond(pose.orientation());
        this.rotationPoint = new Vector3d(pose.rotationPoint());
        this.scale = new Vector3d(pose.scale());
    }

    /**
     * Copies all values from the given pose into this pose.
     *
     * @param pose the pose to copy
     * @return this
     */
    @Contract(value = "_->this", mutates = "this")
    public Pose3d set(final Pose3dc pose) {
        this.position.set(pose.position());
        this.orientation.set(pose.orientation());
        this.rotationPoint.set(pose.rotationPoint());
        this.scale.set(pose.scale());
        return this;
    }

    /**
     * Lerps this pose towards the given pose by the given amount.
     *
     * @param pose the pose to lerp towards
     * @param frac the amount to lerp by, 0.0 to 1.0
     * @return this
     */
    @Contract(value = "_,_->this", mutates = "this")
    public Pose3d lerp(final Pose3dc pose, final double frac) {
        return this.lerp(pose, frac, this);
    }

    /**
     * @return the global position of this pose.
     */
    @Override
    public Vector3d position() {
        return this.position;
    }

    /**
     * @return the global orientation of this pose.
     */
    @Override
    public Quaterniond orientation() {
        return this.orientation;
    }

    /**
     * @return the rotation point of this pose.
     */
    @Override
    public Vector3d rotationPoint() {
        return this.rotationPoint;
    }

    /**
     * @return the global scale of this pose.
     */
    @Override
    public Vector3d scale() {
        return this.scale;
    }

    @Override
    public String toString() {
        final NumberFormat numberFormat = NumberFormat.getInstance();

        return "Pose3d{position=%s, orientation=%s, rotationPoint=%s, scale=%s}"
                .formatted(this.position.toString(numberFormat),
                        this.orientation.toString(numberFormat),
                        this.rotationPoint.toString(numberFormat),
                        this.scale.toString(numberFormat));
    }
}
