package dev.ryanhcode.sable.companion.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import java.util.List;
import java.util.function.Supplier;

@ApiStatus.Internal
public final class SableCompanionUtil {

    public static final Codec<Vector3d> VECTOR_3D_CODEC = Codec.DOUBLE.listOf()
            .comapFlatMap(l -> SableCompanionUtil.fixedSize(l, 3).map(
                            list -> new Vector3d(list.getFirst(), list.get(1), list.get(2))),
                    vec -> List.of(vec.x, vec.y, vec.z));

    public static final Codec<Quaterniond> QUATERNIOND_CODEC = Codec.DOUBLE.listOf()
            .comapFlatMap(l -> SableCompanionUtil.fixedSize(l, 4).map(
                            list -> new Quaterniond(list.getFirst(), list.get(1), list.get(2), list.get(3))),
                    quat -> List.of(quat.x, quat.y, quat.z, quat.w));

    public static <T> DataResult<List<T>> fixedSize(final List<T> list, final int size) {
        if (list.size() != size) {
            final Supplier<String> supplier = () -> "Input is not a list of " + size + " elements";
            return list.size() >= size ? DataResult.error(supplier, list.subList(0, size)) : DataResult.error(supplier);
        } else {
            return DataResult.success(list);
        }
    }
}
