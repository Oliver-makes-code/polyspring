package de.olivermakesco.polyspring.impl;

import de.olivermakesco.polyspring.api.BedrockBlock;
import de.olivermakesco.polyspring.api.BedrockItem;
import de.olivermakesco.polyspring.mixin.CollisionShapeAccessor;
import de.olivermakesco.polyspring.mixin.ShapeCacheAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.block.custom.NonVanillaCustomBlockData;
import org.geysermc.geyser.api.block.custom.component.BoxComponent;
import org.geysermc.geyser.api.block.custom.component.CustomBlockComponents;
import org.geysermc.geyser.api.block.custom.component.GeometryComponent;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomBlocksEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.item.custom.NonVanillaCustomItemData;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

            NonVanillaCustomItemData.Builder data = NonVanillaCustomItemData.builder()
                    .name(bedrockItem.bedrockName())
                    .identifier(location.toString())
                    .javaId(BuiltInRegistries.ITEM.getId(item))
                    .allowOffhand(bedrockItem.bedrockOffhand())
                    .stackSize(item.getDefaultMaxStackSize())
                    .foil(bedrockItem.bedrockFoil())
                    .edible(bedrockItem.bedrockEdible())
                    .chargeable(bedrockItem.bedrockChargeable())
                    .textureSize(bedrockItem.bedrockTextureSize())
                    .translationString(item.getDescriptionId())
                    .icon(icon);

            Optional.ofNullable(bedrockItem.bedrockDamage())
                    .ifPresent(data::maxDamage);

            DataComponentMap components = item.components();

            if (item instanceof ArmorInfo armor) {
                switch (armor.getArmorType()) {
                    case HELMET -> {
                        data.hat(true);
                        data.armorType("helmet");
                    }
                    case CHESTPLATE -> data.armorType("chestplate");
                    case LEGGINGS -> data.armorType("leggings");
                    case BOOTS -> data.armorType("boots");
                    case BODY -> data.armorType("body");
                }
                data.protectionValue(armor.getArmorMaterial().defense().get(armor.getArmorType()));
            }

            if (item instanceof ToolMaterialInterface tool) {
                data.displayHandheld(true);

                switch (item) {
                    case PickaxeItem pickaxeItem -> data.toolType("pickaxe");
                    case AxeItem axeItem -> data.toolType("axe");
                    case HoeItem hoeItem -> data.toolType("hoe");
                    case SwordItem swordItem -> data.toolType("sword");
                    default -> data.toolType("shovel");
                }

                ToolMaterial material = tool.getToolMaterial();
                if (material.equals(ToolMaterial.WOOD)) {
                    data.toolTier("wood");
                } else if (material.equals(ToolMaterial.STONE)) {
                    data.toolTier("stone");
                } else if (material.equals(ToolMaterial.IRON)) {
                    data.toolTier("iron");
                } else if (material.equals(ToolMaterial.GOLD)) {
                    data.toolTier("gold");
                } else if (material.equals(ToolMaterial.DIAMOND)) {
                    data.toolTier("diamond");
                } else {
                    data.toolTier("netherite");
                }
            }

            Optional.ofNullable(components.get(DataComponents.FOOD))
                    .ifPresent(food -> data.canAlwaysEat(food.canAlwaysEat()));

            Optional.ofNullable(components.get(DataComponents.CUSTOM_NAME)).flatMap(name -> Optional.ofNullable(name.tryCollapseToString())).ifPresent(data::displayName);

            Optional.ofNullable(components.get(DataComponents.REPAIRABLE))
                    .ifPresent(repairable -> {
                        Set<String> repairMaterials = new HashSet<>();
                        for (Holder<Item> repairEntry : repairable.items()) {
                            repairMaterials.add(repairEntry.getRegisteredName());
                        }
                        data.repairMaterials(repairMaterials);
                    });



            event.register(data.build());
        }
    }

    @Subscribe
    public void onDefineCustomBlocks(GeyserDefineCustomBlocksEvent event) {
        // Iterate through all blocks and register them if they implement BedrockBlock interface
        for (var entry : BuiltInRegistries.BLOCK.entrySet()) {
            var location = entry.getKey().location();
            var block = entry.getValue();
            if (!(block instanceof BedrockBlock bedrockBlock))
                continue;

            // Collision Box
            BoxComponent collisionBox;
            if (bedrockBlock.bedrockCollisionBox() != null) {
                if (bedrockBlock.bedrockCollisionBox().isEmpty()) {
                    collisionBox = BoxComponent.emptyBox();
                } else {
                    AABB bounding = bedrockBlock.bedrockCollisionBox().bounds();
                    collisionBox = new BoxComponent(
                            (float) bounding.getCenter().x,
                            (float) bounding.getCenter().y,
                            (float) bounding.getCenter().z,
                            (float) bounding.getXsize(),
                            (float) bounding.getYsize(),
                            (float) bounding.getZsize()
                    );
                }
            } else {
                BlockBehaviour.BlockStateBase.Cache cache = ((ShapeCacheAccessor)block.defaultBlockState()).getShapeCache();
                boolean isFullCube = ((CollisionShapeAccessor) (Object) cache).getIsFullCube();
                if (isFullCube) {
                    collisionBox = BoxComponent.fullBox();
                } else {
                    VoxelShape collisionShape = ((CollisionShapeAccessor) (Object) cache).getCollisionShape();
                    if (collisionShape != null && !collisionShape.isEmpty()) {
                        AABB bounding = collisionShape.bounds();
                        collisionBox = new BoxComponent(
                                (float) bounding.getCenter().x,
                                (float) bounding.getCenter().y,
                                (float) bounding.getCenter().z,
                                (float) bounding.getXsize(),
                                (float) bounding.getYsize(),
                                (float) bounding.getZsize()
                        );
                    } else {
                        // Bad assumption, but it's better than crashing :)
                        collisionBox = BoxComponent.emptyBox();
                    }
                }
            }

            // Selection Box
            BoxComponent selectionBox;
            VoxelShape selectionShape = null;
            if (bedrockBlock.bedrockHitBox() != null) {
                selectionShape = bedrockBlock.bedrockHitBox();
            } else {
                try {
                    selectionShape = block.defaultBlockState().getShape(null, BlockPos.ZERO);
                } catch (Exception ignored) {
                }
            }

            if (selectionShape == null || selectionShape.isEmpty()) {
                selectionBox = BoxComponent.emptyBox();
            } else {
                AABB bounding = selectionShape.bounds();
                selectionBox = new BoxComponent(
                        (float) bounding.getCenter().x,
                        (float) bounding.getCenter().y,
                        (float) bounding.getCenter().z,
                        (float) bounding.getXsize(),
                        (float) bounding.getYsize(),
                        (float) bounding.getZsize()
                );
            }
            // Custom Components
            CustomBlockComponents components = CustomBlockComponents.builder()
                    .collisionBox(collisionBox)
                    .selectionBox(selectionBox)
                    .geometry(GeometryComponent.builder()
                            .identifier("geometry." + location.getNamespace() + "." + location.getPath())
                            .build())
                    .lightEmission(block.defaultBlockState().getLightEmission())
                    .lightDampening(block.defaultBlockState().getLightBlock())
                    .friction(block.getFriction())
                    .build();

            NonVanillaCustomBlockData data = NonVanillaCustomBlockData.builder()
                    .name(bedrockBlock.bedrockName())
                    .namespace(location.getNamespace())
                    .components(components)
                    .build();

            event.register(data);
        }
    }
}
