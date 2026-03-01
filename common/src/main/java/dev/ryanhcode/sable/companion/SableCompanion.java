package dev.ryanhcode.sable.companion;

import dev.ryanhcode.sable.companion.math.BoundingBox3dc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.function.BiFunction;

/**
 * @since 1.0.0
 */
@SuppressWarnings("UnstableApiUsage")
public interface SableCompanion {

    /**
     * @since 1.0.0
     */
    @Retention(RetentionPolicy.RUNTIME)
    @interface LoadPriority {
        /**
         * @return The load priority value. The highest priority available companion is loaded
         */
        int value() default 1000;
    }

    /**
     * The companion instance.
     */
    SableCompanion INSTANCE = ServiceLoader.load(SableCompanion.class)
            .stream().max(Comparator.comparingInt(provider -> {
                Class<? extends SableCompanion> type = provider.type();
                LoadPriority annotation = type.getAnnotation(LoadPriority.class);
                return annotation != null ? annotation.value() : 1000;
            }))
            .map(ServiceLoader.Provider::get)
            .orElseThrow(() -> new RuntimeException("Failed to find sable assembly platform"));

    /**
     * Gets all sublevels that intersect with the given bounding box
     *
     * @param bounds The bounding box to check. <br><strong>NOTE: the bounds must NOT be modified during the iteration.
     *               this will cause undefined behavior!</strong>
     */
    @Contract(pure = true)
    Iterable<? extends SubLevelAccess> getAllIntersecting(final Level level, final BoundingBox3dc bounds);

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do
     *
     * @param level  the level to check
     * @param chunkX the global chunk X position
     * @param chunkZ the global chunk Z position
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    @Nullable SubLevelAccess getContaining(final Level level, final int chunkX, final int chunkZ);

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do
     *
     * @param level    the level to check
     * @param chunkPos the global chunk position to check
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    default @Nullable SubLevelAccess getContaining(final Level level, final ChunkPos chunkPos) {
        return this.getContaining(level, chunkPos.x, chunkPos.z);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do
     *
     * @param level the level to check
     * @param pos   the global block position to check
     * @return the sub-level that contains the point, or null if none do
     * @since 1.4.0
     */
    @Contract(pure = true)
    default @Nullable SubLevelAccess getContaining(final Level level, final SectionPos pos) {
        return this.getContaining(level, pos.getX(), pos.getZ());
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do
     *
     * @param level the level to check
     * @param pos   the global block position to check
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    default @Nullable SubLevelAccess getContaining(final Level level, final Vec3i pos) {
        return this.getContaining(level, pos.getX() >> SectionPos.SECTION_BITS, pos.getZ() >> SectionPos.SECTION_BITS);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do
     *
     * @param level the level to check
     * @param pos   the global position to check
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    default @Nullable SubLevelAccess getContaining(final Level level, final Position pos) {
        return this.getContaining(level, Mth.floor(pos.x()) >> SectionPos.SECTION_BITS, Mth.floor(pos.z()) >> SectionPos.SECTION_BITS);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do
     *
     * @param level the level to check
     * @param pos   the global position to check
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    default @Nullable SubLevelAccess getContaining(final Level level, final Vector3dc pos) {
        return this.getContaining(level, Mth.floor(pos.x()) >> SectionPos.SECTION_BITS, Mth.floor(pos.z()) >> SectionPos.SECTION_BITS);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do
     *
     * @param level  the level to check
     * @param blockX the global X position to check
     * @param blockZ the global Z position to check
     * @return the sub-level that contains the point, or null if none do
     * @since 1.2.0
     */
    @Contract(pure = true)
    default @Nullable SubLevelAccess getContaining(final Level level, final double blockX, final double blockZ) {
        return this.getContaining(level, Mth.floor(blockX) >> SectionPos.SECTION_BITS, Mth.floor(blockZ) >> SectionPos.SECTION_BITS);
    }

    /**
     * Gets the sub-level that contains the given entity in its plot, or null if none do
     *
     * @param entity the entity to check
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    default @Nullable SubLevelAccess getContaining(final Entity entity) {
        return this.getContaining(entity.level(), entity.chunkPosition());
    }

    /**
     * Gets the sub-level that contains the given block-entity in its plot, or null if none do
     *
     * @param blockEntity the block-entity to check
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    default @Nullable SubLevelAccess getContaining(final BlockEntity blockEntity) {
        return this.getContaining(blockEntity.getLevel(), blockEntity.getBlockPos());
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do in the client level
     *
     * @param chunkX the global chunk X position
     * @param chunkZ the global chunk Z position
     * @return the sub-level that contains the point, or null if none do
     * @since 1.2.0
     */
    @Contract(pure = true)
    default @Nullable ClientSubLevelAccess getContainingClient(final int chunkX, final int chunkZ) {
        return (ClientSubLevelAccess) this.getContaining(this.getClientLevel(), chunkX, chunkZ);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do in the client level
     *
     * @param chunkPos the global chunk position to check
     * @return the sub-level that contains the point, or null if none do
     * @since 1.2.0
     */
    @Contract(pure = true)
    default @Nullable ClientSubLevelAccess getContainingClient(final ChunkPos chunkPos) {
        return (ClientSubLevelAccess) this.getContaining(this.getClientLevel(), chunkPos);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do in the client level
     *
     * @param pos the global position to check
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    default @Nullable ClientSubLevelAccess getContainingClient(final Position pos) {
        return (ClientSubLevelAccess) this.getContaining(this.getClientLevel(), pos);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do in the client level
     *
     * @param pos the global position to check
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    default @Nullable ClientSubLevelAccess getContainingClient(final Vector3dc pos) {
        return (ClientSubLevelAccess) this.getContaining(this.getClientLevel(), pos);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do in the client level
     *
     * @param pos the global position to check
     * @return the sub-level that contains the point, or null if none do
     * @since 1.4.0
     */
    @Contract(pure = true)
    default @Nullable ClientSubLevelAccess getContainingClient(final SectionPos pos) {
        return (ClientSubLevelAccess) this.getContaining(this.getClientLevel(), pos);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do in the client level
     *
     * @param pos the global position to check
     * @return the sub-level that contains the point, or null if none do
     */
    @Contract(pure = true)
    default @Nullable ClientSubLevelAccess getContainingClient(final Vec3i pos) {
        return (ClientSubLevelAccess) this.getContaining(this.getClientLevel(), pos);
    }

    /**
     * Gets the sub-level that contains the given point in its plot, or null if none do
     *
     * @param blockX the global X position to check
     * @param blockZ the global Z position to check
     * @return the sub-level that contains the point, or null if none do
     * @since 1.2.0
     */
    @Contract(pure = true)
    default @Nullable ClientSubLevelAccess getContainingClient(final double blockX, final double blockZ) {
        return (ClientSubLevelAccess) this.getContaining(this.getClientLevel(), blockX, blockZ);
    }

    /**
     * Gets the sub-level that contains the given entity in its plot, or null if none do
     *
     * @param entity the entity to check
     * @return the sub-level that contains the point, or null if none do
     * @since 1.2.0
     */
    @Contract(pure = true)
    default @Nullable ClientSubLevelAccess getContainingClient(final Entity entity) {
        return (ClientSubLevelAccess) this.getContaining(entity);
    }

    /**
     * Gets the sub-level that contains the given block-entity in its plot, or null if none do
     *
     * @param blockEntity the block-entity to check
     * @return the sub-level that contains the point, or null if none do
     * @since 1.2.0
     */
    @Contract(pure = true)
    default @Nullable ClientSubLevelAccess getContainingClient(final BlockEntity blockEntity) {
        return (ClientSubLevelAccess) this.getContaining(blockEntity);
    }

    /**
     * Projects a point out of a sublevel, if it is within one
     *
     * @param level the level to check
     * @param pos   the point to project
     * @return the projected point
     * @since 1.2.0
     */
    @Contract(value = "_,_->param2", mutates = "param2")
    default Vector3d projectOutOfSubLevel(final Level level, final Vector3d pos) {
        return this.projectOutOfSubLevel(level, pos, pos);
    }

    /**
     * Projects a point out of a sublevel, if it is within one
     *
     * @param level the level to check
     * @param pos   the point to project
     * @return the projected point
     */
    @Contract(value = "_,_,_->param3", mutates = "param3")
    Vector3d projectOutOfSubLevel(final Level level, final Vector3dc pos, final Vector3d dest);

    /**
     * Projects a point out of a sublevel, if it is within one
     *
     * @param level the level to check
     * @param pos   the point to project
     * @return the projected point
     * @deprecated Use {@link #projectOutOfSubLevel(Level, Position)} instead
     */
    @ApiStatus.ScheduledForRemoval(inVersion = "2.0.0")
    @Deprecated
    @Contract(value = "_,_->new", pure = true)
    Vec3 projectOutOfSubLevel(final Level level, final Vec3 pos);

    /**
     * Projects a point out of a sublevel, if it is within one
     *
     * @param level the level to check
     * @param pos   the point to project
     * @return the projected point
     * @since 1.2.0
     */
    @Contract(value = "_,_->new", pure = true)
    default Vec3 projectOutOfSubLevel(final Level level, final Position pos) {
        return this.projectOutOfSubLevel(level, new Vec3(pos.x(), pos.y(), pos.z()));
    }

    /**
     * Checks positions in a level, including sublevels, for a valid position matching the converter to a non-null value.
     * Priority: World -> World, Sub-level -> World, World -> Sub-level
     *
     * @param level             The level
     * @param origin            The origin to check
     * @param shouldCheckOrigin Whether the origin should be checked
     * @param subLevel          The data the origin is assumed to be in
     * @param converter         The function to convert a BlockPos to a non-null value
     * @param <T>               The type of the resulting value
     * @return The resulting value from the converter, or null if nothing is found for all 3 check sets that are done
     * @deprecated Use {@link #runIncludingSubLevels(Level, Position, boolean, SubLevelAccess, BiFunction)} instead
     */
    @ApiStatus.ScheduledForRemoval(inVersion = "2.0.0")
    @Deprecated
    @Nullable
    @Contract(pure = true)
    <T, S extends SubLevelAccess> T runIncludingSubLevels(final Level level, final Vec3 origin, final boolean shouldCheckOrigin, @Nullable final S subLevel, final BiFunction<@Nullable S, BlockPos, T> converter);

    /**
     * Checks positions in a level, including sublevels, for a valid position matching the converter to a non-null value.
     * Priority: World -> World, Sub-level -> World, World -> Sub-level
     *
     * @param level             The level
     * @param origin            The origin to check
     * @param shouldCheckOrigin Whether the origin should be checked
     * @param subLevel          The data the origin is assumed to be in
     * @param converter         The function to convert a BlockPos to a non-null value
     * @param <T>               The type of the resulting value
     * @return The resulting value from the converter, or null if nothing is found for all 3 check sets that are done
     * @since 1.2.0
     */
    @Nullable
    @Contract(pure = true)
    default <T, S extends SubLevelAccess> T runIncludingSubLevels(final Level level, final Position origin, final boolean shouldCheckOrigin, @Nullable final S subLevel, final BiFunction<@Nullable S, BlockPos, T> converter) {
        return this.runIncludingSubLevels(level, new Vec3(origin.x(), origin.y(), origin.z()), shouldCheckOrigin, subLevel, converter);
    }

    /**
     * Checks positions in a level, including sublevels, for a true value from the converter.
     * Priority: World -> World, Sub-level -> World, World -> Sub-level
     *
     * @param level             The level
     * @param origin            The origin to check
     * @param shouldCheckOrigin Whether the origin should be checked
     * @param subLevel          The data the origin is assumed to be in
     * @param converter         The function to convert a BlockPos to a boolean value
     * @return True if any of the checks returned true, otherwise false
     * @deprecated Use {@link #findIncludingSubLevels(Level, Position, boolean, SubLevelAccess, BiFunction)} instead
     */
    @ApiStatus.ScheduledForRemoval(inVersion = "2.0.0")
    @Deprecated
    @Contract(pure = true)
    <S extends SubLevelAccess> boolean findIncludingSubLevels(final Level level, final Vec3 origin, final boolean shouldCheckOrigin, @Nullable final S subLevel, final BiFunction<@Nullable S, BlockPos, Boolean> converter);

    /**
     * Checks positions in a level, including sublevels, for a true value from the converter.
     * Priority: World -> World, Sub-level -> World, World -> Sub-level
     *
     * @param level             The level
     * @param origin            The origin to check
     * @param shouldCheckOrigin Whether the origin should be checked
     * @param subLevel          The data the origin is assumed to be in
     * @param converter         The function to convert a BlockPos to a boolean value
     * @return True if any of the checks returned true, otherwise false
     * @since 1.2.0
     */
    @Contract(pure = true)
    default <S extends SubLevelAccess> boolean findIncludingSubLevels(final Level level, final Position origin, final boolean shouldCheckOrigin, @Nullable final S subLevel, final BiFunction<@Nullable S, BlockPos, Boolean> converter) {
        return this.findIncludingSubLevels(level, new Vec3(origin.x(), origin.y(), origin.z()), shouldCheckOrigin, subLevel, converter);
    }

    /**
     * Computes the distance squared between two points, taking into account sublevels and their plots/poses.
     *
     * @param level the level to check
     * @param a     the first point
     * @param b     the second point
     * @return the distance squared between the two points
     */
    @Contract(pure = true)
    double distanceSquaredWithSubLevels(final Level level, final Vector3dc a, final Vector3dc b);

    /**
     * Computes the distance squared between two points, taking into account sublevels and their plots/poses.
     *
     * @param level the level to check
     * @param a     the first point
     * @param b     the second point
     * @return the distance squared between the two points
     */
    @Contract(pure = true)
    double distanceSquaredWithSubLevels(final Level level, final Position a, final Position b);

    /**
     * Computes the distance squared between two points, taking into account sublevels and their plots/poses.
     *
     * @param level the level to check
     * @param a     the first point
     * @param bX    the second point X
     * @param bY    the second point Y
     * @param bZ    the second point Z
     * @return the distance squared between the two points
     * @since 1.2.0
     */
    @Contract(pure = true)
    double distanceSquaredWithSubLevels(final Level level, final Vector3dc a, final double bX, final double bY, final double bZ);

    /**
     * Computes the distance squared between two points, taking into account sublevels and their plots/poses.
     *
     * @param level the level to check
     * @param a     the first point
     * @param bX    the second point X
     * @param bY    the second point Y
     * @param bZ    the second point Z
     * @return the distance squared between the two points
     * @since 1.2.0
     */
    @Contract(pure = true)
    double distanceSquaredWithSubLevels(final Level level, final Position a, final double bX, final double bY, final double bZ);

    /**
     * Computes the distance squared between two points, taking into account sublevels and their plots/poses.
     *
     * @param level the level to check
     * @param aX    the first point X
     * @param aY    the first point Y
     * @param aZ    the first point Z
     * @param bX    the second point X
     * @param bY    the second point Y
     * @param bZ    the second point Z
     * @return the distance squared between the two points
     * @since 1.2.0
     */
    @Contract(pure = true)
    double distanceSquaredWithSubLevels(final Level level, final double aX, final double aY, final double aZ, final double bX, final double bY, final double bZ);

    /**
     * Gets the global velocity of a point in a level, taking into account sub-level movement.
     *
     * @param level the level to check
     * @param pos   the position of the point
     * @param dest  the vector to hold the result
     * @return the global velocity of the point stored in dest [m/s]
     */
    @Contract(value = "_,_,_->param3", mutates = "param3")
    Vector3d getVelocity(final Level level, final Vector3dc pos, final Vector3d dest);

    /**
     * Gets the global velocity of a point in a level, taking into account sub-level movement.
     *
     * @param level the level to check
     * @param pos   the position of the point
     * @return the global velocity of the point stored in dest [m/s]
     * @since 1.3.0
     */
    @Contract(value = "_,_->param2", mutates = "param2")
    default Vector3d getVelocity(final Level level, final Vector3d pos) {
        return this.getVelocity(level, pos, pos);
    }

    /**
     * Gets the global velocity of a point in a level, taking into account sub-level movement.
     *
     * @param level the level to check
     * @param pos   the position of the point
     * @return the global velocity of the point stored in dest [m/s]
     * @deprecated Use {@link #getVelocity(Level, Position)} instead
     */
    @ApiStatus.ScheduledForRemoval(inVersion = "2.0.0")
    @Deprecated
    @Contract(value = "_,_->new", pure = true)
    Vec3 getVelocity(final Level level, final Vec3 pos);

    /**
     * Gets the global velocity of a point in a level, taking into account sub-level movement.
     *
     * @param level the level to check
     * @param pos   the position of the point
     * @return the global velocity of the point stored in dest [m/s]
     * @since 1.2.0
     */
    @Contract(value = "_,_->new", pure = true)
    default Vec3 getVelocity(final Level level, final Position pos) {
        return this.getVelocity(level, new Vec3(pos.x(), pos.y(), pos.z()));
    }

    /**
     * Gets the global velocity of a point in a sub-level
     *
     * @param level    the level to check
     * @param subLevel the sub-level the point is assumed to be in
     * @param pos      the position of the point
     * @param dest     the vector to hold the result
     * @return the global velocity of the point stored in dest [m/s]
     */
    @Contract(value = "_,_,_,_->param4", mutates = "param4")
    Vector3d getVelocity(final Level level, final SubLevelAccess subLevel, final Vector3dc pos, final Vector3d dest);

    /**
     * Gets the global velocity of a point in a sub-level
     *
     * @param level    the level to check
     * @param subLevel the sub-level the point is assumed to be in
     * @param pos      the position of the point
     * @return the global velocity of the point stored in dest [m/s]
     * @since 1.3.0
     */
    @Contract(value = "_,_,_->param3", mutates = "param3")
    default Vector3d getVelocity(final Level level, final SubLevelAccess subLevel, final Vector3d pos) {
        return this.getVelocity(level, subLevel, pos, pos);
    }

    /**
     * Gets the global velocity of a point in a sub-level
     *
     * @param level    the level to check
     * @param subLevel the sub-level the point is assumed to be in
     * @param pos      the position of the point
     * @return the global velocity of the point stored in dest [m/s]
     * @deprecated Use {@link #getVelocity(Level, SubLevelAccess, Position)} instead
     */
    @ApiStatus.ScheduledForRemoval(inVersion = "2.0.0")
    @Deprecated
    @Contract(value = "_,_,_->new", pure = true)
    Vec3 getVelocity(final Level level, final SubLevelAccess subLevel, final Vec3 pos);

    /**
     * Gets the global velocity of a point in a sub-level
     *
     * @param level    the level to check
     * @param subLevel the sub-level the point is assumed to be in
     * @param pos      the position of the point
     * @return the global velocity of the point stored in dest [m/s]
     * @since 1.2.0
     */
    @Contract(value = "_,_,_->new", pure = true)
    default Vec3 getVelocity(final Level level, final SubLevelAccess subLevel, final Position pos) {
        return this.getVelocity(level, subLevel, new Vec3(pos.x(), pos.y(), pos.z()));
    }

    /**
     * Gets the global velocity of a point in a level relative to the air, taking into account sublevels and their plots/poses
     *
     * @param level the level to check
     * @param pos   the position of the point
     * @param dest  the vector to hold the result
     * @return the global velocity of the point stored in dest [m/s]
     */
    @Contract(value = "_,_,_->param3", mutates = "param3")
    Vector3d getVelocityRelativeToAir(final Level level, final Vector3dc pos, final Vector3d dest);

    /**
     * Gets the global velocity of a point in a level relative to the air, taking into account sublevels and their plots/poses
     *
     * @param level the level to check
     * @param pos   the position of the point
     * @return the global velocity of the point stored in dest [m/s]
     * @since 1.3.0
     */
    @Contract(value = "_,_->param2", mutates = "param2")
    default Vector3d getVelocityRelativeToAir(final Level level, final Vector3d pos) {
        return this.getVelocityRelativeToAir(level, pos, pos);
    }

    /**
     * Gets the global velocity of a point in a level relative to the air, taking into account sublevels and their plots/poses
     *
     * @param level the level to check
     * @param pos   the position of the point
     * @return the global velocity of the point stored in dest [m/s]
     * @deprecated Use {@link #getVelocityRelativeToAir(Level, Position)} instead
     */
    @ApiStatus.ScheduledForRemoval(inVersion = "2.0.0")
    @Deprecated
    @Contract(pure = true)
    Vec3 getVelocityRelativeToAir(final Level level, final Vec3 pos);

    /**
     * Gets the global velocity of a point in a level relative to the air, taking into account sublevels and their plots/poses
     *
     * @param level the level to check
     * @param pos   the position of the point
     * @return the global velocity of the point stored in dest [m/s]
     * @since 1.2.0
     */
    @Contract(pure = true)
    default Vec3 getVelocityRelativeToAir(final Level level, final Position pos) {
        return this.getVelocity(level, new Vec3(pos.x(), pos.y(), pos.z()));
    }

    /**
     * Checks if the plot grid contains the given chunk.
     *
     * @param level  the level to check
     * @param chunkX the global chunk X position
     * @param chunkZ the global chunk Z position
     * @return If the chunk is inside the plot-grid
     */
    @Contract(pure = true)
    boolean isInPlotGrid(final Level level, final int chunkX, final int chunkZ);

    /**
     * Checks if the plot grid contains the given chunk.
     *
     * @param level    the level to check
     * @param chunkPos the global chunk position
     * @return If the chunk is inside the plot-grid
     */
    @Contract(pure = true)
    default boolean isInPlotGrid(final Level level, final ChunkPos chunkPos) {
        return this.isInPlotGrid(level, chunkPos.x, chunkPos.z);
    }

    /**
     * Checks if the plot grid contains the given block.
     *
     * @param level the level to check
     * @param pos   the global position to check
     * @return If the block is inside the plot-grid
     * @since 1.4.0
     */
    @Contract(pure = true)
    default boolean isInPlotGrid(final Level level, final SectionPos pos) {
        return this.isInPlotGrid(level, pos.getX(), pos.getZ());
    }

    /**
     * Checks if the plot grid contains the given block.
     *
     * @param level the level to check
     * @param pos   the global position to check
     * @return If the block is inside the plot-grid
     */
    @Contract(pure = true)
    default boolean isInPlotGrid(final Level level, final Vec3i pos) {
        return this.isInPlotGrid(level, pos.getX() >> SectionPos.SECTION_BITS, pos.getZ() >> SectionPos.SECTION_BITS);
    }

    /**
     * Checks if the plot grid contains the given point.
     *
     * @param level the level to check
     * @param pos   the global position to check
     * @return If the point is inside the plot-grid
     */
    @Contract(pure = true)
    default boolean isInPlotGrid(final Level level, final Position pos) {
        return this.isInPlotGrid(level, Mth.floor(pos.x()) >> SectionPos.SECTION_BITS, Mth.floor(pos.z()) >> SectionPos.SECTION_BITS);
    }

    /**
     * Checks if the plot grid contains the given point.
     *
     * @param level the level to check
     * @param pos   the global position to check
     * @return If the point is inside the plot-grid
     */
    @Contract(pure = true)
    default boolean isInPlotGrid(final Level level, final Vector3dc pos) {
        return this.isInPlotGrid(level, Mth.floor(pos.x()) >> SectionPos.SECTION_BITS, Mth.floor(pos.z()) >> SectionPos.SECTION_BITS);
    }

    /**
     * Checks if the plot grid contains the given entity.
     *
     * @param entity The entity to check
     * @return If the entity is inside the plot-grid
     */
    @Contract(pure = true)
    default boolean isInPlotGrid(final Entity entity) {
        return this.isInPlotGrid(entity.level(), entity.chunkPosition());
    }

    /**
     * Checks if the plot grid contains the given block entity.
     *
     * @param blockEntity The block entity to check
     * @return If the block entity is inside the plot-grid
     */
    @Contract(pure = true)
    default boolean isInPlotGrid(final BlockEntity blockEntity) {
        return this.isInPlotGrid(blockEntity.getLevel(), blockEntity.getBlockPos());
    }

    /**
     * @return The client level instance for #getContainingClient instances
     */
    @ApiStatus.OverrideOnly
    Level getClientLevel();
}
