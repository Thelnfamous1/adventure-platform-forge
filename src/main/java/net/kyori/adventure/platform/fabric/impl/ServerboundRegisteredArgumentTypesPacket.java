/*
 * This file is part of adventure-platform-fabric, licensed under the MIT License.
 *
 * Copyright (c) 2021 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.adventure.platform.fabric.impl;

import net.kyori.adventure.Adventure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * A packet sent client to server, to let the server know which optional argument types are available on the server.
 *
 * <p>This packet is sent by players on join, before the command tree is sent to the client.</p>
 *
 * @param known Known argument type ids
 */
public record ServerboundRegisteredArgumentTypesPacket(Set<ResourceLocation> known) {
  public static final ResourceLocation ID = new ResourceLocation(Adventure.NAMESPACE, "registered_args");

  public static void register() {
    /*
    ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buffer, responder) -> {
      final ServerboundRegisteredArgumentTypesPacket pkt = ServerboundRegisteredArgumentTypesPacket.of(buffer);
      server.execute(() -> { // on main thread
        ServerArgumentTypes.knownArgumentTypes(player, pkt.known, responder);
      });
    });
     */
  }

  public void handle(Supplier<NetworkEvent.Context> ctx){
    ctx.get().enqueueWork(() -> {
      ServerPlayer player = ctx.get().getSender();
      ServerArgumentTypes.knownArgumentTypes(player, this.known);
    });
    ctx.get().setPacketHandled(true);
  }

  public static ServerboundRegisteredArgumentTypesPacket of(final Set<ResourceLocation> idents) {
    return new ServerboundRegisteredArgumentTypesPacket(Set.copyOf(idents));
  }

  public static ServerboundRegisteredArgumentTypesPacket of(final @NotNull FriendlyByteBuf buf) {
    final int length = buf.readVarInt();
    final Set<ResourceLocation> items = new HashSet<>();
    for (int i = 0; i < length; ++i) {
      items.add(buf.readResourceLocation());
    }
    return of(items);
  }

  public void toPacket(final FriendlyByteBuf buffer) {
    buffer.writeVarInt(this.known.size());
    for (final ResourceLocation id : this.known) {
      buffer.writeResourceLocation(id);
    }
  }

}
