package org.abstruck.emotional_block.handle;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.registries.RegistryManager;
import org.abstruck.emotional_block.types.IEmotionType;

import java.util.concurrent.atomic.AtomicReference;

public class EmotionHandler {
    public static void setEmotion(Level level, BlockPos pos, IEmotionType emotion) {
        LevelChunk chunk = level.getChunkAt(pos);
        chunk.getCapability(CapabilityHandler.EMOTION_CAP).ifPresent(cap -> {
            cap.setEmotion(pos, emotion);
        });
    }

    public static IEmotionType getEmotionFromID(ResourceLocation id) {
        IEmotionType type = IEmotionType.getDefaultEmotion();
        try {
            type = RegistryManager.ACTIVE.getRegistry(IEmotionType.EMOTIONS).getRaw(id);
        }
        catch (NullPointerException ignored) { }
        return type;
    }

    public static ResourceLocation getNamespaceFromEmotion(IEmotionType type) {
        try {
            ResourceLocation name = RegistryManager.ACTIVE.getRegistry(IEmotionType.EMOTIONS).getKey(type);
            return name;
        }
        catch (NullPointerException ignored) { }

        return IEmotionType.getDefaultEmotionID();
    }

    public static IEmotionType getEmotion(Level level, BlockPos pos) {
        AtomicReference<IEmotionType> type = new AtomicReference<>(IEmotionType.getDefaultEmotion());
        LevelChunk chunk = level.getChunkAt(pos);
        chunk.getCapability(CapabilityHandler.EMOTION_CAP).ifPresent(cap -> {
            type.set(cap.getEmotion(pos));
        });
        return type.get();
    }

    public static boolean hasEmotion(Level level, BlockPos pos) {
        IEmotionType type = EmotionHandler.getEmotion(level, pos);
        return (IEmotionType.getDefaultEmotion() != type);
    }
}
