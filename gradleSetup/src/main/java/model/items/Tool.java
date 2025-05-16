package model.items;

import com.google.gson.internal.bind.TreeTypeAdapter;
import controller.GameMenuController.GameController;
import model.App;
import model.Enums.Animals.AnimalType;
import model.Enums.GameObjects.TreeType;
import model.Enums.Items.*;
import model.Enums.TileType;
import model.Enums.WeatherAndTime.Seasons;
import model.Enums.WeatherAndTime.WeatherType;

import model.GameObject.*;
import model.MapModule.Tile;
import model.Player;
import model.Result;
import model.skills.Skill;

import java.util.ArrayList;

public class Tool extends Item {
    //    protected ToolBehavior toolBehavior;
//    protected ToolMaterial toolMaterial;
    private ToolType toolType;
    private int capacity;

    public Tool(ToolType toolType) {
        super(toolType.getName(), 100, true, -1);
        this.toolType = toolType;
        this.capacity = toolType.getCapacity();
//        this.toolBehavior = toolBehavior;
//        this.toolBehavior.setToolType();
//        this.toolMaterial = toolMaterial;
    }

    public static void upgrade(Tool tool){
        if (tool.getToolType().getNextToolType() != null){
            tool.setToolType(tool.getToolType());
        }
    }

    public void use(Tile tile){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        switch (name){
            case "Axe" : {
                Skill playerSkill = player.getSkillByName("foraging");
                if (tile.getFixedObject().getClass() == Tree.class){
                    TreeType t = ((Tree)tile.getFixedObject()).getTreeType();
                    switch (t){
                        case TreeType.NORMAL_TREE -> player.getInventory().add(new Etc((EtcType) TreeType.NORMAL_TREE.fruit) , 1);
                        case TreeType.BURNT_TREE -> player.getInventory().add(new Mineral((MineralItemType)TreeType.BURNT_TREE.fruit) , 1);
                        case TreeType.TREE_BARK -> player.getInventory().add(new Etc((EtcType) TreeType.TREE_BARK.fruit) , 1);
                        default -> player.getInventory().add(new Fruit((FruitType) t.fruit) , 1);
                    }
                    tile.setFixedObject(null);
//                    player.getInventory().add(new Etc(EtcType.WOOD) , 1);
//                    Skill playerSkill = player.getSkillByName("foraging");

                    if(playerSkill==null){
                        System.out.println("Player skill is null in tool use");
                        return;
                    }
                    playerSkill.setXp(playerSkill.getXp() + 5);
                    if (playerSkill.getLevel() == 3){
                        player.subtractEnergy(toolType.getUsedEnergy() +1);
                    } else {
                        player.subtractEnergy(toolType.getUsedEnergy());
                    }
                } else {
                    if (playerSkill.getLevel() == 3){
                        if (toolType.getUsedEnergy() -2 >= 0){
                            player.subtractEnergy(toolType.getUsedEnergy() +2);
                        }
                    } else {
                        player.subtractEnergy(toolType.getUsedEnergy() + 1);
                    }

                }

                break;
            }
            case "Hoe" : {
                if (tile.getFixedObject() == null && tile.getTileType() == TileType.Soil){
                    tile.setTileType(TileType.PlowedSoil);
                }
                player.subtractEnergy(toolType.getUsedEnergy());
                break;
            }
            case "Pickaxe" : {
                Skill playerSkill = player.getSkillByName("mining");
                if (tile.getFixedObject().getClass() == ForagingMineral.class){///minerals
                    player.getInventory().add(new Mineral(((ForagingMineral)tile.getFixedObject()).getForagingMineralType()) , 1);
                    tile.setFixedObject(null);
                    if (playerSkill!=null){
                        playerSkill.setXp(playerSkill.getXp() + 10);
                    }
                    player.subtractEnergy(toolType.getUsedEnergy());
                } else if (tile.getFixedObject() == null && tile.getTileType() == TileType.PlowedSoil) {
                    tile.setTileType(TileType.Soil);
                    player.subtractEnergy(toolType.getUsedEnergy());
                } else if(tile.getFixedObject().getClass() == DroppedItem.class){
                    tile.setFixedObject(null);
                    player.subtractEnergy(toolType.getUsedEnergy());
                } else {
                    player.subtractEnergy(toolType.getUsedEnergy() + 1);
                }
//                player.setEnergy(player.getEnergy() - toolType.getUsedEnergy());
                break;
            }
            case "Watering Can" : {
                Skill playerSkill = player.getSkillByName("farming");
                if (tile.getFixedObject().getClass() == Tree.class || tile.getFixedObject().getClass() == Crop.class || tile.getTileType() == TileType.Water){
                    if (tile.getTileType() == TileType.Water){
                        this.capacity = toolType.getCapacity();
                    } else {
                        this.capacity--;
                        if (tile.getFixedObject().getClass() == Tree.class){
                            ((Tree)tile.getFixedObject()).setWateredToday(true);////interface plantable??
                        } else if (tile.getFixedObject().getClass() == Crop.class) {
                            ((Tree)tile.getFixedObject()).setWateredToday(true);
                        }
//
                    }
                    player.subtractEnergy(toolType.getUsedEnergy());
                    if (playerSkill.getLevel() == 3){
                        player.addEnergy( 1);
                    }
                }
            }
            case "Fishing Pole" : {
                Skill playerSkill = player.getSkillByName("fishing");
                if (tile.getTileType() == TileType.Water){
                    int quantity = (int) (Math.random() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplier() * (playerSkill.getLevel() + 2));
                    if (toolType.getToolMaterial() == ToolMaterial.Training){
                        FishType fishType = FishType.getCheapestFishOfSeason(App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason());
                        player.getInventory().add(new Fish(fishType), quantity);
                    } else {
                        player.getInventory().add(new Fish(FishType.values()[(int)(Math.random() * FishType.values().length)]), quantity);
                    }
                }
                player.subtractEnergy(toolType.getUsedEnergy());
                if (playerSkill.getLevel() == 3){
                    player.addEnergy(1);
                }
            }
            case "Scythe" : {
                if (tile.getFixedObject().getClass() == Crop.class || tile.getFixedObject().getClass() == ForagingCrop.class){
                    if (tile.getFixedObject().getClass() == Crop.class){
                        Crop crop = (Crop) tile.getFixedObject();
                        player.getInventory().add(new Food(crop.getCropType().cropItem) , 1);
                    } else {
                        ForagingCrop foragingCrop = (ForagingCrop) tile.getFixedObject();
                        player.getInventory().add(new Food(foragingCrop.getForagingCropType().cropItem) , 1);
                    }
                }
                player.subtractEnergy(toolType.getUsedEnergy());
            }
            case "Milk Pail" : {
                if (tile.getFixedObject().getClass() == Animal.class){
                    Animal animal = (Animal) tile.getFixedObject();
                    if (animal.getAnimalInfo() == AnimalType.COW || animal.getAnimalInfo() == AnimalType.SHEEP || animal.getAnimalInfo() == AnimalType.GOAT){
                        player.getInventory().add(new Etc(EtcType.MILK) , 1);
                        animal.setGetProduct(true);
                    }
                    player.getInventory().remove(new Tool(ToolType.MILK_PAIL) , 1);
                }
                player.subtractEnergy(toolType.getUsedEnergy());
            }
            case "Shear" : {
                if (tile.getFixedObject().getClass() == Animal.class){
                    Animal animal = (Animal) tile.getFixedObject();
                    if (animal.getAnimalInfo() == AnimalType.SHEEP){
                        player.getInventory().add(new Etc(EtcType.WOOL) , 1);
                        animal.setGetProduct(true);
                    }
                }
                player.subtractEnergy(toolType.getUsedEnergy());
            }
            if(player.isFainted()){
                GameController.skipTurn();
            }
        }
    }

    public ToolType getToolType() {
        return toolType;
    }
    public void setToolType(ToolType toolType){
        this.toolType = toolType;
    }

    public int getCapacity() {
        return capacity;
    }
}
