package model.items.Tools;

import model.Enums.Items.ToolMaterial;
import model.Locations.Tile;
import model.items.Tool;
import model.items.ToolBehavior;

public class WateringCan extends Tool {

    public WateringCan(ToolBehavior toolBehavior, ToolMaterial toolMaterial) {
        super(toolBehavior , toolMaterial);
    }

    @Override
    public void use(Tile tile) {
        toolBehavior.useOn(tile);
    }

}
