package model.items.Tools;

import model.Enums.Items.ToolMaterial;
import model.Locations.Tile;
import model.items.Tool;
import model.items.ToolBehavior;

public class Shear extends Tool {

    public Shear(ToolBehavior toolBehavior, ToolMaterial toolMaterial , String name, int maxStackSize, boolean Stackable) {
        super(toolBehavior , toolMaterial, name, maxStackSize, Stackable);
    }

    @Override
    public void use(Tile tile) {
        toolBehavior.useOn(tile);
    }
}
