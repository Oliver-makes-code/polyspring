package de.olivermakesco.polyspring.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class PolySpringMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
            PolySpringUtils.registerGeyserEvents();
        });
    }
}
