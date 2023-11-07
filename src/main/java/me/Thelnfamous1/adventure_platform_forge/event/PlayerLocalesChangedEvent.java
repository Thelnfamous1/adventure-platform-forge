package me.Thelnfamous1.adventure_platform_forge.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class PlayerLocalesChangedEvent extends Event {

    private final ServerPlayer player;
    private final Locale newLocale;

    public PlayerLocalesChangedEvent(@NotNull ServerPlayer player, @Nullable Locale newLocale){
        this.player = player;
        this.newLocale = newLocale;
    }

    public ServerPlayer getPlayer() {
        return this.player;
    }

    public Locale getNewLocale() {
        return this.newLocale;
    }
}
