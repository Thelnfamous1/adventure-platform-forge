package me.Thelnfamous1.adventure_platform_forge.network;

import me.Thelnfamous1.adventure_platform_forge.AdventurePlatformForge;
import net.kyori.adventure.platform.fabric.impl.ClientboundArgumentTypeMappingsPacket;
import net.kyori.adventure.platform.fabric.impl.ServerboundRegisteredArgumentTypesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class AdventureNetwork {


    private static final ResourceLocation CHANNEL_NAME = new ResourceLocation(AdventurePlatformForge.MODID, "network");
    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel SYNC_CHANNEL = NetworkRegistry.newSimpleChannel(
            CHANNEL_NAME,
            () -> PROTOCOL_VERSION,
            clientVersion -> PROTOCOL_VERSION.equals(clientVersion) || NetworkRegistry.ABSENT.equals(clientVersion),
            serverVersion -> PROTOCOL_VERSION.equals(serverVersion) || NetworkRegistry.ABSENT.equals(serverVersion)
    );

    public static void init() {
        SYNC_CHANNEL.registerMessage(0, ServerboundRegisteredArgumentTypesPacket.class, ServerboundRegisteredArgumentTypesPacket::toPacket, ServerboundRegisteredArgumentTypesPacket::of, ServerboundRegisteredArgumentTypesPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        SYNC_CHANNEL.registerMessage(1, ClientboundArgumentTypeMappingsPacket.class, ClientboundArgumentTypeMappingsPacket::toPacket, ClientboundArgumentTypeMappingsPacket::from, ClientboundArgumentTypeMappingsPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

}
