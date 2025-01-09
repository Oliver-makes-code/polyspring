# PolySpring

A Polymer style library for adding content to Bedrock (via Geyser)

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
