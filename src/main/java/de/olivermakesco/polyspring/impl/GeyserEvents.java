package de.olivermakesco.polyspring.impl;

import de.olivermakesco.polyspring.api.BedrockItem;
import net.minecraft.core.registries.BuiltInRegistries;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.item.custom.NonVanillaCustomItemData;

public class GeyserEvents implements EventRegistrar {
    @Subscribe
    public void onGeyserDefineCustomItemsEvent(GeyserDefineCustomItemsEvent event) {
        // Iterate through all items and register them if they implement the BedrockItem interface
        for (var entry : BuiltInRegistries.ITEM.entrySet()) {
            var location = entry.getKey().location();
            var item = entry.getValue();
            if (!(item instanceof BedrockItem bedrockItem))
                continue;

            var icon = location.toString().replace(":", "_");

            NonVanillaCustomItemData data = NonVanillaCustomItemData.builder()
                    .name(bedrockItem.bedrockName())
                    .identifier(location.toString())
                    .javaId(BuiltInRegistries.ITEM.getId(item))
                    .allowOffhand(bedrockItem.bedrockOffhand())
                    .stackSize(item.getDefaultMaxStackSize())
                    .foil(bedrockItem.bedrockFoil())
                    .edible(bedrockItem.bedrockEdible())
                    .icon(icon)
                    .build();

            event.register(data);
        }
    }
}
