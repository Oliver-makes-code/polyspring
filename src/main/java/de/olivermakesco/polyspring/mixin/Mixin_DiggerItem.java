package de.olivermakesco.polyspring.mixin;

import de.olivermakesco.polyspring.impl.Duck_ToolMaterialInterface;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DiggerItem.class)
public class Mixin_DiggerItem implements Duck_ToolMaterialInterface {
    private ToolMaterial material;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void polyspring$readToolMaterial(ToolMaterial toolMaterial, TagKey tagKey, float f, float g, Item.Properties properties, CallbackInfo ci) {
        this.material = toolMaterial;
    }

    @Override
    public ToolMaterial polyspring$getToolMaterial() {
        return this.material;
    }
}
