package me.Thelnfamous1.adventure_platform_forge;

import com.mojang.logging.LogUtils;
import me.Thelnfamous1.adventure_platform_forge.network.AdventureNetwork;
import net.kyori.adventure.platform.fabric.impl.AdventureCommon;
import net.kyori.adventure.platform.fabric.impl.client.AdventureClient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AdventurePlatformForge.MODID)
public class AdventurePlatformForge {
    public static final String MODID = "adventure_platform_forge";
    public static final Logger LOGGER = LogUtils.getLogger();
    private final AdventureCommon adventureCommon;
    private final AdventureClient adventureClient;

    public AdventurePlatformForge() {
        this.adventureCommon = new AdventureCommon();
        this.adventureClient = new AdventureClient();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
                this.adventureCommon.onInitialize();
                AdventureNetwork.init();
        });
    }

    private void onClientSetup(FMLClientSetupEvent event){
        event.enqueueWork(this.adventureClient::onInitializeClient);
    }
}
