package org.abstruck.emotional_block.handle;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.abstruck.emotional_block.EmotionalBlock;
import org.abstruck.emotional_block.capabilities.ChunkEmotionCapability;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = EmotionalBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityHandler {
    public static final Capability<ChunkEmotionCapability> EMOTION_CAP = CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public static void registerCap(RegisterCapabilitiesEvent event) {
        event.register(ChunkEmotionCapability.class);
    }

    @SubscribeEvent
    public static void attachChunkCap(AttachCapabilitiesEvent<LevelChunk> event) {
        event.addCapability(ResourceLocation.fromNamespaceAndPath(EmotionalBlock.MOD_ID, "emotion_data"), new ICapabilitySerializable<CompoundTag>() {
            private final ChunkEmotionCapability cap = new ChunkEmotionCapability();
            private final LazyOptional<ChunkEmotionCapability> optional = LazyOptional.of(() -> cap);

            @Override
            public CompoundTag serializeNBT() {
                return cap.serializeNBT();
            }

            @Override
            public void deserializeNBT(CompoundTag nbt) {
                cap.deserializeNBT(nbt);
            }

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
                return EMOTION_CAP.orEmpty(capability, optional);
            }
        });
    }
}

