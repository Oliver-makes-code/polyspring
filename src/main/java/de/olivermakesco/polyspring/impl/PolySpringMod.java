package de.olivermakesco.polyspring.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.geysermc.geyser.api.GeyserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolySpringMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Polysprint");

    public static Item test;

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
            PolySpringUtils.registerGeyserEvents();
        });

        // Register test item to test systems
        var key = ResourceLocation.fromNamespaceAndPath("polyspring", "test_item");
        test = Registry.register(BuiltInRegistries.ITEM, key, new TestItem(
                new Item.Properties()
                        .stacksTo(16)
                        .rarity(Rarity.EPIC)
                        .setId(ResourceKey.create(Registries.ITEM, key))
        ));
    }
}
