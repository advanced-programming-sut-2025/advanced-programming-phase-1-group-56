package model.items;

import model.App;
import model.Enums.Items.ToolType;
import model.Enums.TileType;
import model.GameObject.Tree;
import model.MapModule.Tile;
import model.Player;
import model.skills.Skill;

public class Tool extends Item {
    //    protected ToolBehavior toolBehavior;
//    protected ToolMaterial toolMaterial;
    private ToolType toolType;

    public Tool(String name, ToolType toolType ,int maxStackSize, boolean Stackable) {
        super(name,maxStackSize,Stackable);
        this.toolType = toolType;
//        this.toolBehavior = toolBehavior;
//        this.toolBehavior.setToolType();
//        this.toolMaterial = toolMaterial;
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
                    player.setEnergy(player.getEnergy() - toolType.getUsedEnergy());
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
