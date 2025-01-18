package de.olivermakesco.polyspring.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.BlockStateBase.Cache.class)
public interface Accessor_CollisionShape {
    @Accessor("collisionShape")
    VoxelShape getCollisionShape();

    @Accessor("isCollisionShapeFullBlock")
    boolean getIsFullCube();
}