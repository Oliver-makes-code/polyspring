package de.olivermakesco.polyspring.api;

import de.olivermakesco.polyspring.impl.PolySpringUtils;
import net.minecraft.server.level.ServerPlayer;

public class GeyserPlayers {
    /// Checks if a player is a geyser player.
    ///
    /// Doesn't require Geyser to be loaded
    public static boolean isGeyserPlayer(ServerPlayer player) {
        return PolySpringUtils.isGeyserPlayer(player);
    }
}
