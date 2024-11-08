package de.olivermakesco.polyspring.impl;

import de.olivermakesco.polyspring.api.BedrockItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.item.custom.NonVanillaCustomItemData;

public class PolySpringMod implements ModInitializer, EventRegistrar {
    public static Item test;

    @Override
    public void onInitialize() {
        if (PolySpringUtils.isGeyserLoaded())
            ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
                GeyserApi.api().eventBus().register(this, this);
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

    @Subscribe
    public void onGeyserDefineCustomItemsEvent(GeyserDefineCustomItemsEvent event) {
        // Iterate through all items and register them if they implement the BedrockItem interface
        for (var entry : BuiltInRegistries.ITEM.entrySet()) {
            var location = entry.getKey().location();
            var item = entry.getValue();
            if (!(item instanceof BedrockItem bedrockItem))
                continue;

            NonVanillaCustomItemData data = NonVanillaCustomItemData.builder()
                    .name(bedrockItem.getBedrockName())
                    .identifier(location.toString())
                    .javaId(BuiltInRegistries.ITEM.getId(item))
                    .allowOffhand(bedrockItem.allowOffhand())
                    .stackSize(item.getDefaultMaxStackSize())
                    .build();

            event.register(data);
        }
    }
}
