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
    public Iterable<SubLevelAccess> getAllIntersecting(Level level, BoundingBox3dc bounds) {
        return List.of();
    }

    @Override
    public @Nullable SubLevelAccess getContaining(Level level, int chunkX, int chunkZ) {
        return null;
    }

    @Override
    public Vector3d projectOutOfSubLevel(Level level, Vector3dc pos, Vector3d dest) {
        return dest.set(pos);
    }

    @Override
    public Vec3 projectOutOfSubLevel(Level level, Vec3 pos) {
        return pos;
    }

    @Override
    public @Nullable <T> T runIncludingSubLevels(Level level, Vec3 origin, boolean shouldCheckOrigin, @Nullable SubLevelAccess subLevel, BiFunction<@Nullable SubLevelAccess, BlockPos, T> converter) {
        return shouldCheckOrigin ? converter.apply(subLevel, BlockPos.containing(origin.x, origin.y, origin.z)) : null;
    }

    @Override
    public boolean findIncludingSubLevels(Level level, Vec3 origin, boolean shouldCheckOrigin, @Nullable SubLevelAccess subLevel, BiFunction<@Nullable SubLevelAccess, BlockPos, Boolean> converter) {
        return shouldCheckOrigin ? converter.apply(subLevel, BlockPos.containing(origin.x, origin.y, origin.z)) : false;
    }

    @Override
    public double distanceSquaredWithSubLevels(Level level, Vector3dc a, Vector3dc b) {
        return a.distanceSquared(b);
    }

    @Override
    public double distanceSquaredWithSubLevels(Level level, Vec3 a, Vec3 b) {
        return a.distanceToSqr(b);
    }

    @Override
    public Vector3d getVelocity(Level level, Vector3dc pos, Vector3d dest) {
        return dest.zero();
    }

    @Override
    public Vec3 getVelocity(Level level, Vec3 pos) {
        return Vec3.ZERO;
    }

    @Override
    public Vector3d getVelocity(Level level, SubLevelAccess subLevel, Vector3dc pos, Vector3d dest) {
        return dest.zero();
    }

    @Override
    public Vec3 getVelocity(Level level, SubLevelAccess subLevel, Vec3 pos) {
        return Vec3.ZERO;
    }

    @Override
    public Vector3d getVelocityRelativeToAir(Level level, Vector3dc pos, Vector3d dest) {
        return dest.zero();
    }

    @Override
    public Vec3 getVelocityRelativeToAir(Level level, Vec3 pos) {
        return Vec3.ZERO;
    }

    @Override
    public boolean isInPlotGrid(Level level, int chunkX, int chunkZ) {
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
