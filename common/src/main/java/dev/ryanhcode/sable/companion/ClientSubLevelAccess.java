package dev.ryanhcode.sable.companion;

import dev.ryanhcode.sable.companion.math.Pose3dc;

/**
 * @since 1.0.0
 */
public interface ClientSubLevelAccess extends SubLevelAccess {

    /**
     * @return The pose used for sub-level rendering with the current frame partial-tick
     */
    Pose3dc renderPose();

    /**
     * @return The pose used for sub-level rendering with a particular partial-tick
     */
    Pose3dc renderPose(float partialTick);

}
