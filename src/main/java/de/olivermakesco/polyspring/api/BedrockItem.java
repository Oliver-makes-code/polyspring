package de.olivermakesco.polyspring.api;

/// Implement this on your Item class to enable bedrock compatibility
public interface BedrockItem {
    /// Gets the default display name of the item
    String bedrockName();

    /// Whether to allow the item to be in the offhand.
    default boolean bedrockOffhand() {
        return true;
    }

    ///  Whether the item is edible
    default boolean bedrockEdible() {
        return false;
    }

    /// Whether the item should have a foil
    default boolean bedrockFoil() {
        return false;
    }
}
