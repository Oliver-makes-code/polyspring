package de.olivermakesco.polyspring.api;

import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public interface BedrockBlock {
    /// Gets the default display name of the block
    String bedrockName();

    /// Gets the collision box of the block
    @Nullable
    default VoxelShape bedrockCollisionBox() {
        return null;
    }

    ///  Gets the outline/hitbox of the block
    @Nullable
    default VoxelShape bedrockHitBox() {
        return null;
    }
}
