package org.abstruck.emotional_block;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import org.abstruck.emotional_block.init.InitDataComponent;
import org.abstruck.emotional_block.init.InitEmotion;
import org.abstruck.emotional_block.types.IEmotionType;
import org.slf4j.Logger;

@Mod(EmotionalBlock.MOD_ID)
public class EmotionalBlock {

    public static final String MOD_ID = "emotional_block";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EmotionalBlock(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus bus = context.getModEventBus();

        bus.addListener(this::onRegistryEmotionType);
        InitEmotion.EMOTIONS.register(bus);
        InitDataComponent.COMPONENTS.register(bus);
    }

    private void onRegistryEmotionType(NewRegistryEvent event) {
        RegistryBuilder<IEmotionType> builder = new RegistryBuilder<>();
        builder.setName(IEmotionType.EMOTIONS.location());
        builder.setDefaultKey(IEmotionType.getDefaultEmotionID());
        event.create(builder);
    }

}
