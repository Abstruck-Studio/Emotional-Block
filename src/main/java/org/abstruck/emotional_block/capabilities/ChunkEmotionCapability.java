package org.abstruck.emotional_block.capabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.abstruck.emotional_block.types.IEmotionType;

import java.util.HashMap;
import java.util.Map;

public class ChunkEmotionCapability {
    private final Map<BlockPos, IEmotionType> emotionMap = new HashMap<>();

    public IEmotionType getEmotion(BlockPos pos) {
        if (!emotionMap.containsKey(pos)) {
            emotionMap.put(pos, IEmotionType.getDefaultEmotion());
        }

        return emotionMap.get(pos);
    }

    public void setEmotion(BlockPos pos, IEmotionType type) {
        if (emotionMap.containsKey(pos)) {
            emotionMap.replace(pos, type);
            return;
        }
        emotionMap.put(pos, type);
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        emotionMap.forEach((pos, type) -> {
            tag.put(pos.asLong() + "", type.serialize());
        });
        return tag;
    }

    public void deserializeNBT(CompoundTag nbt) {
        emotionMap.clear();
        for (String key : nbt.getAllKeys()) {
            BlockPos pos = BlockPos.of(Long.parseLong(key));
            IEmotionType type = IEmotionType.getDefaultEmotion();
            try {
                type = type.deserialize(nbt.getCompound(key));
            }
            catch (NullPointerException ignored) {

            }

            emotionMap.put(pos, type);
        }
    }
}
