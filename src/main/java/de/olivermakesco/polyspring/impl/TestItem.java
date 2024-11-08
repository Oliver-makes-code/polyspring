package de.olivermakesco.polyspring.impl;

import de.olivermakesco.polyspring.api.BedrockItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import xyz.nucleoid.packettweaker.PacketContext;

public class TestItem extends SimplePolymerItem implements BedrockItem {
    public TestItem(Properties properties) {
        super(properties, Items.OAK_BOAT);
    }

    @Override
    public String getBedrockName() {
        return "test";
    }

    @Override
    public boolean allowOffhand() {
        return true;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        if (PolySpringUtils.isGeyserPlayer(context.getPlayer()))
            return this;
        return super.getPolymerItem(itemStack, context);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipFlag tooltipType, PacketContext context) {
        if (PolySpringUtils.isGeyserPlayer(context.getPlayer()))
            return itemStack;
        return super.getPolymerItemStack(itemStack, tooltipType, context);
    }
}
