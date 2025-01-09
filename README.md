# PolySpring

A Polymer style library for adding content to Bedrock (via Geyser)

You can safely embed this library in your mod, without requiring Geyser.
If it doesn't work without Geyser, open an issue and send me your log.

It is currently a work in progress. I'm currently adding features I need for my serverside mods.
If you want to add support for other things, send a Pull Request

## Adding an item
- Implement the `BedrockItem` class
  - You only need to override the `bedrockName` method, but other methods can be overridden as well.
- Register it normally

```java
public class MyItem extends Item implements BedrockItem {
    //...
    
    @Override
    public String bedrockName() {
        return "my_item";
    }
}
```

## Checking if a player is connected via Geyser (Polymer Example)
- Use `GeyserPlayers.isGeyserPlayer`, it doesn't need Geyser loaded to work.

```java
public class MyItem extends SimplePolymerItem implements BedrockItem {
    //...
    
    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        if (GeyserPlayers.isGeyserPlayer(context.getPlayer()))
            return this;
        return super.getPolymerItem(itemStack, context);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipFlag tooltipType, PacketContext context) {
        if (GeyserPlayers.isGeyserPlayer(context.getPlayer()))
            return itemStack;
        return super.getPolymerItemStack(itemStack, tooltipType, context);
    }
}
```
