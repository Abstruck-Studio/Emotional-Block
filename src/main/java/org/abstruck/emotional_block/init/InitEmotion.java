package org.abstruck.emotional_block.init;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.abstruck.emotional_block.EmotionalBlock;
import org.abstruck.emotional_block.types.IEmotionType;

public class InitEmotion {
    public static final DeferredRegister<IEmotionType> EMOTIONS =
            DeferredRegister.create(IEmotionType.EMOTIONS, EmotionalBlock.MOD_ID);

    public static final RegistryObject<IEmotionType> DEFAULT =
            EMOTIONS.register("default", () -> (IEmotionType) () -> 0xFFFFFF);

    public static final RegistryObject<IEmotionType> HAPPY =
            EMOTIONS.register("happy", () -> new IEmotionType() {

                @Override
                public void onBreak(Level level, Player player, BlockState state, BlockPos pos) {
                    player.sendSystemMessage(Component.literal("Happy!"));
                }

                @Override
                public int getColor() {
                    return 0x66CDAA;
                }
            });
}
