package model.items;

import model.Enums.Items.ToolMaterial;
import model.MapModule.Tile;

public abstract class Tool extends Item {
    protected ToolBehavior toolBehavior;
    protected ToolMaterial toolMaterial;

    public Tool(ToolBehavior toolBehavior , ToolMaterial toolMaterial,String name, int maxStackSize, boolean Stackable ) {
        super(name,maxStackSize,Stackable);
        this.toolBehavior = toolBehavior;
        this.toolMaterial = toolMaterial;
    }

    public abstract void use(Tile tile);
}
