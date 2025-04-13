package model.items.Tools;

import model.Enums.Items.ToolMaterial;
import model.locations.Tile;
import model.items.Tool;
import model.items.ToolBehavior;

public class FishingPole extends Tool {

    public FishingPole(ToolBehavior toolBehavior, ToolMaterial toolMaterial) {
        super(toolBehavior , toolMaterial);
    }

    @Override
    public void use(Tile tile) {
        toolBehavior.useOn(tile);
    }
}
