package model.items;

import model.Enums.ToolMaterial;
import model.Locations.Tile;

public class WateringCan extends Tool {

    public WateringCan(ToolBehavior toolBehavior, ToolMaterial toolMaterial) {
        super(toolBehavior , toolMaterial);
    }

    @Override
    public void use(Tile tile) {
        toolBehavior.useOn(tile);
    }

}
