package dev.ryanhcode.sable.companion.impl;

import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import dev.ryanhcode.sable.companion.math.BoundingBox3dc;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Default implementation of {@link SableCompanion} for when Sable is missing at runtime.
 */
@ApiStatus.Internal
@SableCompanion.LoadPriority(500)
public final class DefaultSableCompanion implements SableCompanion {

    @Override
    public Iterable<SubLevelAccess> getAllIntersecting(final Level level, final BoundingBox3dc bounds) {
        return List.of();
    }

    @Override
    public @Nullable SubLevelAccess getContaining(final Level level, final int chunkX, final int chunkZ) {
        return null;
    }

    @Override
    public Vector3d projectOutOfSubLevel(final Level level, final Vector3dc pos, final Vector3d dest) {
        return dest.set(pos);
    }

    @Override
    public Vec3 projectOutOfSubLevel(final Level level, final Vec3 pos) {
        return pos;
    }

    @Override
    public @Nullable <T> T runIncludingSubLevels(final Level level, final Vec3 origin, final boolean shouldCheckOrigin, @Nullable final SubLevelAccess subLevel, final BiFunction<@Nullable SubLevelAccess, BlockPos, T> converter) {
        return shouldCheckOrigin ? converter.apply(subLevel, BlockPos.containing(origin.x, origin.y, origin.z)) : null;
    }

    @Override
    public boolean findIncludingSubLevels(final Level level, final Vec3 origin, final boolean shouldCheckOrigin, @Nullable final SubLevelAccess subLevel, final BiFunction<@Nullable SubLevelAccess, BlockPos, Boolean> converter) {
        return shouldCheckOrigin ? converter.apply(subLevel, BlockPos.containing(origin.x, origin.y, origin.z)) : false;
    }

    @Override
    public double distanceSquaredWithSubLevels(final Level level, final Vector3dc a, final Vector3dc b) {
        return a.distanceSquared(b);
    }

    @Override
    public double distanceSquaredWithSubLevels(final Level level, final Vec3 a, final Vec3 b) {
        return a.distanceToSqr(b);
    }

    @Override
    public Vector3d getVelocity(final Level level, final Vector3dc pos, final Vector3d dest) {
        return dest.zero();
    }

    @Override
    public Vec3 getVelocity(final Level level, final Vec3 pos) {
        return Vec3.ZERO;
    }

    @Override
    public Vector3d getVelocity(final Level level, final SubLevelAccess subLevel, final Vector3dc pos, final Vector3d dest) {
        return dest.zero();
    }

    @Override
    public Vec3 getVelocity(final Level level, final SubLevelAccess subLevel, final Vec3 pos) {
        return Vec3.ZERO;
    }

    @Override
    public Vector3d getVelocityRelativeToAir(final Level level, final Vector3dc pos, final Vector3d dest) {
        return dest.zero();
    }

    @Override
    public Vec3 getVelocityRelativeToAir(final Level level, final Vec3 pos) {
        return Vec3.ZERO;
    }

    @Override
    public boolean isInPlotGrid(final Level level, final int chunkX, final int chunkZ) {
        return false;
    }

    @Override
    public Level getClientLevel() {
        return DistHelper.getClientLevel();
    }

    private static final class DistHelper {
        public static Level getClientLevel() {
            return Minecraft.getInstance().level;
        }
    }
}
