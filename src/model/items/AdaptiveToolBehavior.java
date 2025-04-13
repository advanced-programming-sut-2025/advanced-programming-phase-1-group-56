package model.items;

import model.Enums.Items.ToolMaterial;
import model.Enums.Items.ToolType;
import model.Locations.Tile;
import model.Player;

public class AdaptiveToolBehavior implements ToolBehavior {
    private ToolType toolType;
    private Player player;
    private ToolMaterial toolMaterial;

    public AdaptiveToolBehavior(ToolType toolType, Player player , ToolMaterial toolMaterial) {
        this.toolType = toolType;
        this.player = player;
        this.toolMaterial = toolMaterial;
    }

    @Override
    public void useOn(Tile tile) {
        //TODO
        // if
            // if
                // if
    }
}
