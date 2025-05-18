package model.items;

import controller.GameMenuController.GameController;
import model.App;
import model.Enums.Animals.AnimalType;
import model.Enums.GameObjects.TreeType;
import model.Enums.Items.*;
import model.Enums.Skills;
import model.Enums.TileType;

import model.GameObject.*;
import model.MapModule.Tile;
import model.Player;
import model.skills.Skill;

public class Tool extends Item {
    private ToolType toolType;
    private int capacity;

    public Tool(ToolType toolType) {
        super(toolType.getName(), 100, true, -1);
        this.toolType = toolType;
        this.capacity = 0;
    }

    public static void upgrade(Tool tool) {
        if (tool.getToolType().getNextToolType() != null) {
            tool.setToolType(tool.getToolType());
        }
    }

    public void use(Tile tile) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        switch (name) {
            case "Axe": {
                Skill playerSkill = player.getSkillByName(Skills.Foraging.toString());
                if (tile.getFixedObject() != null && tile.getFixedObject().getClass() == Tree.class) {
                    TreeType t = ((Tree) tile.getFixedObject()).getTreeType();
                    switch (t) {
                        case TreeType.BURNT_TREE ->
                                player.getInventory().add(new Mineral((MineralItemType) TreeType.BURNT_TREE.fruit), 1);
                        case TreeType.NORMAL_TREE -> player.getInventory().add(new Etc((EtcType) t.fruit), 1);
                        case TreeType.TREE_BARK -> player.getInventory().add(new Etc((EtcType) t.fruit), 1);
                        default -> player.getInventory().add(new Etc((EtcType) TreeType.NORMAL_TREE.fruit), 1);
                    }
                    if (t != TreeType.BURNT_TREE && t != TreeType.TREE_BARK && t != TreeType.NORMAL_TREE) {
                        App.getMe().getInventory().add(new Seed((SeedType) t.source), 1);
                        if (Math.random() > 0.5) {
                            App.getMe().getInventory().add(new Seed((SeedType) t.source), 1);
                        }
                    }
                    tile.setFixedObject(null);
                    if (playerSkill == null) {
                        System.out.println("Player skill is null in tool use");
                        return;
                    }
                    playerSkill.setXp(playerSkill.getXp() + 5);
                    if (playerSkill.getLevel() == 3) {
                        player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool() + 1);
                        System.out.println(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool() + 1);
                    } else {
                        player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                        System.out.println(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                    }
                } else {
                    if (playerSkill.getLevel() == 3) {
                        if (toolType.getUsedEnergy() - 2 >= 0) {
                            player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool() + 2);
                            System.out.println(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool() + 2);
                        }
                    } else {
                        player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool() + 1);
                        System.out.println(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool() + 1);
                    }

                }

                break;
            }
            case "Hoe": {
                if (tile.getFixedObject() == null && tile.getTileType() == TileType.Soil) {
                    tile.setTileType(TileType.PlowedSoil);
                }
                player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                break;
            }
            case "Pickaxe": {
                Skill playerSkill = player.getSkillByName(Skills.Mining.toString());
                if (tile.getFixedObject() instanceof ForagingMineral) {///minerals
                    player.getInventory().add(new Mineral(((ForagingMineral) tile.getFixedObject()).getForagingMineralType()), 1);
                    tile.setFixedObject(null);
                    if (playerSkill != null) {
                        playerSkill.setXp(playerSkill.getXp() + 10);
                        App.getMe().getSkillByName(Skills.Foraging.toString()).setXp(App.getMe().getSkillByName(Skills.Foraging.toString()).getXp() + 10);
                    }
                    player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                } else if (tile.getFixedObject() == null && (tile.getTileType() == TileType.PlowedSoil || tile.getTileType() == TileType.WaterPlowedSoil
                        || tile.getTileType() == TileType.Speed_Gro || tile.getTileType() == TileType.Deluxe_Retaining_Soil)) {
                    tile.setTileType(TileType.Soil);
                    player.subtractEnergy(toolType.getUsedEnergy() * (int) App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                } else if (tile.getFixedObject() instanceof DroppedItem) {
                    player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
//                } else if (tile.getFixedObject().getClass() == DroppedItem.class) {
//                    tile.setFixedObject(null);
//                    player.subtractEnergy(toolType.getUsedEnergy() * (int) App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                } else if (tile.getFixedObject() instanceof Crop && !((Crop) tile.getFixedObject()).getCropType().oneTime) {
                    tile.setFixedObject(null);
                    player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());

                } else {
                    player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool() + 1);
                }
                break;
            }
            case "Watering Can": {
                Skill playerSkill = player.getSkillByName(Skills.Farming.toString());
                if (tile.getFixedObject() instanceof Tree || tile.getFixedObject() instanceof Crop || tile.getTileType() == TileType.Water) {
                    if (tile.getTileType() == TileType.Water) {
                        this.capacity = toolType.getCapacity();
                    } else {
                        this.capacity--;
                        if (tile.getFixedObject().getClass() == Tree.class) {
                            ((Tree) tile.getFixedObject()).setWateredToday(true);
                        } else if (tile.getFixedObject().getClass() == Crop.class) {
                            ((Crop) tile.getFixedObject()).setWateredToday(true);
                        }
//
                    }
                    player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                    if (playerSkill.getLevel() == 3) {
                        player.addEnergy(1);
                    }
                }
                break;
            }
            case "Fishing Pole": {
                Skill playerSkill = player.getSkillByName(Skills.Fishing.toString());
                if (tile.getTileType() == TileType.Water) {
                    int quantity = (int) (Math.random() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplier() * (playerSkill.getLevel() + 2));
                    if (toolType.getToolMaterial() == ToolMaterial.Training) {
                        FishType fishType = FishType.getCheapestFishOfSeason(App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason());
                        player.getInventory().add(new Fish(fishType), quantity);
                    } else {
                        player.getInventory().add(new Fish(FishType.getSeasonFishes(App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason()).get(2)), quantity);
                    }
                }
                player.subtractEnergy(toolType.getUsedEnergy());
                App.getMe().getSkillByName(Skills.Fishing.toString()).setXp(App.getMe().getSkillByName(Skills.Fishing.toString()).getXp() + 5);
                if (playerSkill.getLevel() == 3) {
                    player.addEnergy(1);
                }
                break;
            }
            case "Scythe": {
                if (tile.getFixedObject() instanceof Crop || tile.getFixedObject() instanceof ForagingCrop) {
                    if (tile.getFixedObject().getClass() == Crop.class) {
                        Crop crop = (Crop) tile.getFixedObject();
                        player.getInventory().add(new Food(crop.getCropType().cropItem), 1);
                        if (crop.isIs1time()) {
                            tile.setFixedObject(null);
                            App.getCurrentUser().getCurrentGame().getTimeSystem().removeObserver(crop);
                        } else {
                            crop.setHarvest(false);
                            crop.setHarvestDayRegrowth(0);
                        }
                    } else {
                        ForagingCrop foragingCrop = (ForagingCrop) tile.getFixedObject();
                        player.getInventory().add(new Food(foragingCrop.getForagingCropType().cropItem), 1);
                        tile.setFixedObject(null);
                    }
                } else if (tile.getFixedObject() instanceof Tree) {
                    Tree tree = (Tree) tile.getFixedObject();
                    TreeType treeType = tree.getTreeType();
                    if (treeType != TreeType.BURNT_TREE && treeType != TreeType.TREE_BARK && treeType != TreeType.NORMAL_TREE) {
                        App.getMe().getInventory().add(new Fruit((FruitType) treeType.fruit), 1);
                        tree.setHarvestDayRegrowth(0);
                        tree.setHarvest(false);
                    }
                }
                player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                break;
            }
            case "Milk Pail": {
                if (tile.getFixedObject() instanceof Animal) {
                    Animal animal = (Animal) tile.getFixedObject();
                    if (animal.getAnimalInfo() == AnimalType.COW || animal.getAnimalInfo() == AnimalType.SHEEP) {
                        player.getInventory().add(new Etc(EtcType.MILK), 1);
                        animal.deleteProduct(EtcType.MILK);
                    } else if (animal.getAnimalInfo() == AnimalType.GOAT) {
                        player.getInventory().add(new Etc(EtcType.GOAT_MILK), 1);
                        animal.deleteProduct(EtcType.GOAT_MILK);
                        App.getMe().getSkillByName(Skills.Farming.toString()).setXp(App.getMe().getSkillByName(Skills.Farming.toString()).getXp() + 5);
                    }
                }
                player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                break;
            }
            case "Shear": {
                if (tile.getFixedObject() instanceof Animal) {
                    Animal animal = (Animal) tile.getFixedObject();
                    if (animal.getAnimalInfo() == AnimalType.SHEEP) {
                        player.getInventory().add(new Etc(EtcType.WOOL), 1);
                        animal.deleteProduct(EtcType.WOOL);
                        App.getMe().getSkillByName(Skills.Farming.toString()).setXp(App.getMe().getSkillByName(Skills.Farming.toString()).getXp() + 5);
                    }
                }
                player.subtractEnergy(toolType.getUsedEnergy() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplierTool());
                break;
            }
//            if (player.isFainted()) {
//                GameController.skipTurn();
//            }
        }
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public int getCapacity() {
        return capacity;
    }
}
