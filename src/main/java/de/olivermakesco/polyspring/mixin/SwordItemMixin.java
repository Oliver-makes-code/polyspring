package de.olivermakesco.polyspring.mixin;

import de.olivermakesco.polyspring.impl.ToolMaterialInterface;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SwordItem.class)
public class SwordItemMixin implements ToolMaterialInterface {
    private ToolMaterial material;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void polyspring$readSwordMaterial(ToolMaterial toolMaterial, float f, float g, Item.Properties properties, CallbackInfo ci) {
        this.material = toolMaterial;
    }

    @Override
    public ToolMaterial getToolMaterial() {
        return this.material;
    }
}
