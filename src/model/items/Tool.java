package model.items;

import model.Enums.Items.ToolMaterial;
import model.locations.Tile;

public abstract class Tool extends Item {
    protected ToolBehavior toolBehavior;
    protected ToolMaterial toolMaterial;

    public Tool(ToolBehavior toolBehavior , ToolMaterial toolMaterial) {
        this.toolBehavior = toolBehavior;
        this.toolMaterial = toolMaterial;
    }

    public abstract void use(Tile tile);
}
