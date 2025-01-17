package de.olivermakesco.polyspring.mixin;

import de.olivermakesco.polyspring.impl.Duck_ArmorInfo;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorItem.class)
public class Mixin_ArmorItem implements Duck_ArmorInfo {
    private ArmorMaterial material;
    private ArmorType type;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void polyspring$getArmorData(ArmorMaterial armorMaterial, ArmorType armorType, Item.Properties properties, CallbackInfo ci) {
        this.material = armorMaterial;
        this.type = armorType;
    }

    @Override
    public ArmorType polyspring$getArmorType() {
        return this.type;
    }

    @Override
    public ArmorMaterial polyspring$getArmorMaterial() {
        return this.material;
    }
}
