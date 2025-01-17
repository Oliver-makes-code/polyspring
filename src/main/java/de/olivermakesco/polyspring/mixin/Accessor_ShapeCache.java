package de.olivermakesco.polyspring.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.BlockStateBase.class)
public interface Accessor_ShapeCache {
    @Accessor("cache")
    BlockBehaviour.BlockStateBase.Cache getShapeCache();
}
