package model.items;

import model.Enums.ToolMaterial;
import model.Locations.Tile;

public class Scythe extends Tool{

    public Scythe(ToolBehavior toolBehavior, ToolMaterial toolMaterial) {
        super(toolBehavior , toolMaterial);
    }
    @Override
    public void use(Tile tile) {
        toolBehavior.useOn(tile);
    }
}
