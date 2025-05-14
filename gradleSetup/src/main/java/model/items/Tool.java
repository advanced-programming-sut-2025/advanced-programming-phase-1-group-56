package model.items;

import model.App;
import model.Enums.Items.ToolType;
import model.Enums.TileType;
import model.GameObject.Tree;
import model.MapModule.Tile;
import model.Player;
import model.skills.Skill;

public class Tool extends Item {
    private ToolType toolType;

    public Tool(ToolType toolType) {
        super(toolType.name(),1,false,-1);
        this.toolType = toolType;
    }

    public void use(Tile tile){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        switch (name){
            case "Axe" : {
                if (tile.getFixedObject().getClass() == Tree.class || tile.getFixedObject().getClass() == Wood.class){
                    tile.setFixedObject(null);
                    player.getInventory().add(new TreeWood());//TODO
                    Skill playerSkill = player.getSkillByName("farming");
                    if (playerSkill!=null){
                        playerSkill.setXp(playerSkill.getXp() + 5);
                    }
                    player.addEnergy(-toolType.getUsedEnergy());
                }
                break;
            }
            case "Hoe" : {
                if (tile.getFixedObject() == null && tile.getTileType() == TileType.Soil){
                    tile.setTileType(TileType.PlowedSoil);
                }
                player.setEnergy(player.getEnergy() - toolType.getUsedEnergy());
                break;
            }
            case "Pickaxe" : {
                if (tile.getFixedObject().getClass() == Stone.class){///minerals
                    tile.setFixedObject(null);
                    player.getInventory().add();
                    Skill playerSkill = player.getSkillByName("mining");
                    if (playerSkill!=null){
                        playerSkill.setXp(playerSkill.getXp() + 10);
                    }
                } else if (tile.getFixedObject() == null && tile.getTileType() == TileType.PlowedSoil) {
                    tile.setTileType(TileType.Soil);
                }
                player.setEnergy(player.getEnergy() - toolType.getUsedEnergy());
                break;
            }
        }
    }

    public ToolType getToolType() {
        return toolType;
    }

}
