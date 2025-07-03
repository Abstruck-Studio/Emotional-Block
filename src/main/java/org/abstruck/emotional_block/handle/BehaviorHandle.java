package org.abstruck.emotional_block.handle;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.abstruck.emotional_block.init.InitEmotion;
import org.abstruck.emotional_block.types.IEmotionType;

@Mod.EventBusSubscriber
public class BehaviorHandle {

    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        Player player = event.getPlayer();
        BlockState state = event.getState();
        BlockPos pos = event.getPos();

        if ((levelAccessor instanceof Level level) && (EmotionHandler.hasEmotion(level, pos))) {
            IEmotionType type = EmotionHandler.getEmotion(level, pos);
            type.onBreak(level, player, state, pos);
        }
    }

    @SubscribeEvent
    public static void onUseBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        EmotionHandler.setEmotion(level, pos, InitEmotion.HAPPY.get());
    }
}
