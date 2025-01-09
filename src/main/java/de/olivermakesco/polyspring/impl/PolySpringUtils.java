package de.olivermakesco.polyspring.impl;

import net.minecraft.server.level.ServerPlayer;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.jetbrains.annotations.Nullable;

public class PolySpringUtils {
    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isGeyserLoaded() {
        return classExists("org.geysermc.geyser.api.GeyserApi");
    }

    public static @Nullable GeyserConnection getConnection(ServerPlayer player) {
        var geyser = GeyserApi.api();
        return geyser.connectionByUuid(player.getUUID());
    }

    private static boolean isGeyserPlayerReal(ServerPlayer player) {
        return getConnection(player) != null;
    }

    public static boolean isGeyserPlayer(ServerPlayer player) {
        if (isGeyserLoaded())
            return isGeyserPlayerReal(player);
        return false;
    }

    private static void registerGeyserEventsReal() {
        var events = new GeyserEvents();
        GeyserApi.api().eventBus().register(events, events);
    }

    public static void registerGeyserEvents() {
        if (isGeyserLoaded())
            registerGeyserEventsReal();
    }
}
