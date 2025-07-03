package org.abstruck.emotional_block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryManager;
import org.abstruck.emotional_block.EmotionalBlock;
import org.abstruck.emotional_block.init.InitEmotion;

public interface IEmotionType {

    ResourceKey<Registry<IEmotionType>> EMOTIONS =
            ResourceKey.createRegistryKey(
                    ResourceLocation.fromNamespaceAndPath(EmotionalBlock.MOD_ID, "emotion_types")
            );

    default void apply (Level level, BlockState state, BlockPos pos) { }

    default void onBreak(Level level, Player player, BlockState state, BlockPos pos) { }

    int getColor();

    default CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putString("emotion", RegistryManager.ACTIVE.getRegistry(IEmotionType.EMOTIONS).getKey(this).getNamespace());
        return tag;
    }

    default IEmotionType deserialize(CompoundTag tag) {
        return RegistryManager.ACTIVE.getRegistry(IEmotionType.EMOTIONS).getRaw(ResourceLocation.parse(tag.getString("emotion")));
    }

    static IEmotionType getDefaultEmotion() {
        return InitEmotion.DEFAULT.get();
    }

    static ResourceLocation getDefaultEmotionID() {
        return InitEmotion.DEFAULT.getId();
    }

}
