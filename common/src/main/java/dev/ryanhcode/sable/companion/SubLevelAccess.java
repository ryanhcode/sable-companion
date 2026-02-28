package dev.ryanhcode.sable.companion;

import dev.ryanhcode.sable.companion.math.BoundingBox3dc;
import dev.ryanhcode.sable.companion.math.Pose3dc;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Access to
 * @since 1.0.0
 */
public interface SubLevelAccess {

    /**
     * @return The current pose of this sub-level
     */
    Pose3dc logicalPose();

    /**
     * @return The pose of this sub-level from the previous tick
     */
    Pose3dc lastPose();

    /**
     * @return The global bounding box of this sub-level
     */
    BoundingBox3dc boundingBox();

    /**
     * The UUID of a sub-level is networked and consistent across saving/loading
     *
     * @return The UUID of this sub-level
     */
    UUID getUniqueId();

    /**
     * @return The display name of this sub-level, if present.
     */
    @Nullable String getName();

}
