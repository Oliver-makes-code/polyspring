package de.olivermakesco.polyspring.impl;

import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public interface ArmorInfo {
    ArmorType getArmorType();
    ArmorMaterial getArmorMaterial();
}
