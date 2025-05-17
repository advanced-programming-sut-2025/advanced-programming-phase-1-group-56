package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.BuffType;
import model.Enums.Direction;
import model.Enums.GameObjects.CropType;
import model.Enums.GameObjects.ForagingCropType;
import model.Enums.GameObjects.TreeType;
import model.Enums.Items.*;
import model.Enums.Skills;
import model.Enums.TileType;
import model.Enums.WeatherAndTime.Seasons;
import model.Game;
import model.GameObject.*;
import model.MapModule.GameLocations.Farm;
import model.MapModule.Position;
import model.MapModule.Tile;
import model.Result;
import model.Slot;
import model.items.Item;
import model.items.Seed;
import model.items.Tool;
import model.skills.MiningSkill;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;

public class FarmingController extends CommandController {

    public static void manageStrikeThunder(Farm farm){
        if (App.getCurrentUser().getCurrentGame().getWeatherState().shouldStrikeThunder()) {
            for (int i = 0; i < 3; i++) {
                int randX = (int) (Math.random() * farm.getTiles()[0].length);
                int randY = (int) (Math.random() * farm.getTiles().length);
                WeatherController.cheatThor(farm, Integer.toString(randX), Integer.toString(randY));
            }
        }
    }
    public static Result craftInfo(Matcher matcher) {
        String name = matcher.group(1);
        FruitType fruitType = null;
        CropType cropType = null;
        TreeType treeType = null;
        ForagingCropType foragingCropType = null;
        SeedType seedType = null;

        for (FruitType fruitType1 : FruitType.values()) {
            if (name.equals(fruitType1.name())) {
                fruitType = fruitType1;
            }
        }

        StringBuilder tmpString = new StringBuilder();

        if (fruitType != null) {
            tmpString.append("Name: ").append(fruitType.getName()).append("\n");
            tmpString.append("Energy: ").append(fruitType.getEnergy()).append("\n");
            tmpString.append("Price: ").append(fruitType.getPrice()).append("\n");
            tmpString.append("BuffType: ").append(fruitType.getBuffType().toString()).append("\n");

        } else {
            for (CropType cropType1 : CropType.values()) {
                if (name.equals(cropType1.name())) {
                    cropType = cropType1;
                }
            }
            if (cropType != null) {
                tmpString.append("Name: ").append(cropType.cropItem.getName()).append("\n");
                for (SeedType seedType1 : SeedType.values()) {
                    if (((CropType) seedType1.cropType).cropItem.name().equals(cropType.cropItem.name())) {
                        tmpString.append("Source: ").append(seedType1.name).append("\n");
                    }
                }
                tmpString.append("Stage: ");
                for (int i = 0; i < cropType.stages.length; i++) {
                    tmpString.append(i);
                    if (i != cropType.stages.length - 1) {
                        tmpString.append("-");
                    }
                }
                tmpString.append("\n");
                tmpString.append("Total Harvest Time: ").append(cropType.totalHarvestTime).append("\n");
                tmpString.append("One Time: ").append(cropType.oneTime).append("\n");
                tmpString.append("Regrowth Time: ").append(cropType.regrowthTime == -1 ? "None" : cropType.regrowthTime).append("\n");
                tmpString.append("Base Sell Price: ").append(cropType.baseSellPrice).append("\n");
                tmpString.append("Is Edible: ").append(cropType.isEdible).append("\n");
                tmpString.append("Base Energy: ").append(cropType.energy).append("\n");
                tmpString.append("Season: ").append(Arrays.toString(cropType.season)).append("\n");
                tmpString.append("Can Become Giant: ").append(cropType.canBecomeGiant).append("\n");

            } else {
                for (TreeType treeType1 : TreeType.values()) {
                    if (name.equals(treeType1.name())) {
                        treeType = treeType1;
                    }
                }
                if (treeType != null) {
                    tmpString.append("Name: ").append(treeType.name).append("\n");
                    tmpString.append("Source: ").append(treeType.source).append("\n");
                    tmpString.append("Stages: ").append(treeType.stages).append("\n");
                    tmpString.append("Total Harvest Time: ").append(treeType.totalHarvestTime).append("\n");
                    tmpString.append("Fruit: ").append(treeType.fruit != null ? ((FruitType) treeType.fruit).getName() : "None").append("\n");
                    tmpString.append("Harvest Cycle: ").append(treeType.fruitHarvestCycle).append("\n");
                    tmpString.append("Fruit Base Sell Price: ").append(treeType.fruitBaseSellPrice).append("\n");
                    tmpString.append("Is Fruit Edible: ").append(treeType.isFruitEdible).append("\n");
                    tmpString.append("Fruit Energy: ").append(treeType.fruitEnergy).append("\n");
                    tmpString.append("Season: ").append(Arrays.toString(treeType.season)).append("\n");

                } else {
                    for (SeedType seedType1 : SeedType.values()) {
                        if (name.equals(seedType1.name())) {
                            seedType = seedType1;
                        }
                    }
                    if (seedType != null) {
                        tmpString.append("Name: ").append(seedType.name).append("\n");
                        tmpString.append("Season: ").append(Arrays.toString(seedType.season)).append("\n");
                        tmpString.append("Grows into: ").append(seedType.cropType instanceof CropType
                                ? ((CropType) seedType.cropType).cropItem.getName()
                                : seedType.cropType instanceof TreeType
                                ? ((TreeType) seedType.cropType).name
                                : "Unknown").append("\n");
                    } else {
                        for (ForagingCropType foragingCropType1 : ForagingCropType.values()) {
                            if (name.equals(foragingCropType1.name())) {
                                foragingCropType = foragingCropType1;
                            }
                        }
                        if (foragingCropType != null) {
                            tmpString.append("Name: ").append(foragingCropType.cropItem.getName()).append("\n");
                            tmpString.append("Season: ").append(Arrays.toString(foragingCropType.season)).append("\n");
                            tmpString.append("Base Sell Price: ").append(foragingCropType.baseSellPrice).append("\n");
                            tmpString.append("Energy: ").append(foragingCropType.energy).append("\n");
                        } else {
                            return new Result(false, "crop, fruit, tree, seed or foraging crop with such name does not exist!");
                        }
                    }
                }
            }
        }

        return new Result(true, tmpString.toString());
    }

    public static void managePlaceMineral(Farm farm) {
        Random random = new Random();
        int randomMinerals =  random.nextInt(MineralItemType.values().length);
        MineralItemType mineralItemType = MineralItemType.values()[randomMinerals];
        for (int i = 0; i < farm.getTiles().length; i++) {
            for (int j = 0; j < farm.getTiles()[i].length; j++) {
                Tile tile = farm.getTiles()[i][j];
                if (tile.getFixedObject() == null && random.nextInt(4) == 0 && tile.getTileType() == TileType.Mine) {
                    tile.setFixedObject(new ForagingMineral(false, new Position(i, j), mineralItemType));
                }
            }
        }
    }

    public static Result manageBigCrop() {

        return null;
    }

    public static SeedType manageMixedSeed() {
        Seasons season = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason();
        MixedSeed mixedSeed = MixedSeed.fromSeason(season);

        Random random = new Random();
        int index = random.nextInt(mixedSeed.possibleCrops.length);
        SeedType selectedSeed = mixedSeed.possibleCrops[index];

        return selectedSeed;
    }

    public static Result manageCrows(Farm farm) {
        Random random = new Random();
        int randomInt = random.nextInt(3);
        if(randomInt == 0) {
            int count = 0;
            for(int i = 0 ; i < farm.getAllGameObjects().size() ; i++) {
                if(farm.getAllGameObjects().get(i) instanceof Crop||farm.getAllGameObjects().get(i) instanceof Tree) {
                    count++;
                }
            }
            for(int i = 0 ; i < count ; i++) {
                if (farm.getAllGameObjects().get(i) instanceof Crop && !((Crop)farm.getAllGameObjects().get(i)).isProtected()) {
                    farm.getTileByPosition(farm.getAllGameObjects().get(i).getPosition().getX(), farm.getAllGameObjects().get(i).getPosition().getY()).setFixedObject(null);
                }else if(farm.getAllGameObjects().get(i) instanceof Tree && !((Tree)farm.getAllGameObjects().get(i)).isProtected()) {
                    ((Tree)farm.getTileByPosition(farm.getAllGameObjects().get(i).getPosition().getX(), farm.getAllGameObjects().get(i).getPosition().getY()).getFixedObject()).setHarvest(false);
                    ((Tree)farm.getTileByPosition(farm.getAllGameObjects().get(i).getPosition().getX(), farm.getAllGameObjects().get(i).getPosition().getY()).getFixedObject()).setHarvestDayRegrowth(0);
                }
                if(i+16 < count){
                    i+=16;
                }else{
                    break;
                }
            }
        }
        return null;
    }

    public static Result managePlantSeed(Matcher matcher) {
        String seedName = matcher.group(1);
        String direction = matcher.group(2);
        Direction dir;
        SeedType seed = getSeedTypeFromString(seedName);
        Item seed1 = getItemFromString(seedName);
        if (seed1 == null) {
            return new Result(false, "this seed does not exist!");
        } else if ((dir = getDirectionFromString(direction)) == null) {
            return new Result(false, "this direction does not exist!");
        } else if (!(App.getMe().getCurrentGameLocation() instanceof Farm || App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation() == App.getMe().getPlayerFarm().getGreenHouse().getIndoor())){
            return new Result(false, "you are not in Green House or Farm!");
        }
        Position position = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPosition();
        int x = position.getX();
        int y = position.getY();
        switch (dir) {
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            case UPLEFT:
                x -= 1;
                y -= 1;
                break;
            case UPRIGHT:
                x += 1;
                y -= 1;
                break;
            case DOWNLEFT:
                x -= 1;
                y += 1;
                break;
            case DOWNRIGHT:
                x += 1;
                y += 1;
                break;
            default:
                break;
        }
        if (containsSeason(seed.season,App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason())) {
            if (!(App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation() == App.getMe().getPlayerFarm().getGreenHouse().getIndoor()
                    || App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation() == App.getMe().getPartner().getPlayerFarm().getGreenHouse().getIndoor()))
                return new Result(false, "you can't plant in this season!");
        }


        if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).getTileType() != TileType.PlowedSoil
        ||App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).getTileType() != TileType.WaterPlowedSoil
        ||App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).getTileType() != TileType.Deluxe_Retaining_Soil
        ||App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).getTileType() != TileType.Speed_Gro ) {
            return new Result(false, "you can't plant in this tile!");
        }

        if (!App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).isWalkable()) return new Result(false, "you can't plant in this tile!");
        SeedType seed2 = seed;
        if(seed.name.equals(SeedType.MIXED.name())) {
            seed2 = manageMixedSeed();
        }
        if (seed2.cropType instanceof CropType) {
            Crop crop =  new Crop(true, new Position(x, y), (CropType) seed.cropType);
            if(App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation() == App.getMe().getPlayerFarm().getGreenHouse().getIndoor()
                    || App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation() == App.getMe().getPartner().getPlayerFarm().getGreenHouse().getIndoor()){
                crop.setInGreenHouse(true);
                crop.setProtected(true);
            }
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).setFixedObject(crop);
        } else if (seed2.cropType instanceof TreeType) {
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).setFixedObject(new Tree(((TreeType) seed.cropType), new Position(x, y)));
        }
        App.getMe().getInventory().remove(seed1,1);
        App.getMe().getSkillByName(Skills.Farming.toString()).setXp(App.getMe().getSkillByName(Skills.Farming.toString()).getXp()+5);
        return new Result(true, "you successfully planted in this tile!");
    }

    public static Result showPlant(Matcher matcher) {
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        Tile tile = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y);
        StringBuilder tmpString = new StringBuilder();
        if (tile.getFixedObject() instanceof Tree) {
            Tree tree = (Tree) tile.getFixedObject();
            tmpString.append("Name: ").append(tree.getTreeType().name()).append("\n");
            int remainHarvestTime = tree.getTreeType().totalHarvestTime;
            for (int i = 0; i < tree.getCurrentStage(); i++) {
                remainHarvestTime -= tree.getTreeType().stages;
            }
            tmpString.append("remaining Harvest time : ").append(remainHarvestTime).append("\n");
            tmpString.append("Current Stage : ").append(tree.getCurrentStage()).append("\n");
            tmpString.append("is Watered Today : ").append(tree.isWateredToday()).append("\n");
            tmpString.append("is fertilized Today : ").append(tree.isFertilizerToday()).append("\n");
        } else if (tile.getFixedObject() instanceof Crop) {
            Crop crop = (Crop) tile.getFixedObject();
            tmpString.append("Name: ").append(crop.getCropType().name()).append("\n");
            int remainHarvestTime = crop.getCropType().totalHarvestTime;
            for (int i = 0; i < crop.getCurrentStage(); i++) {
                remainHarvestTime -= crop.getCropType().stages[i];
            }
            tmpString.append("remaining Harvest time : ").append(remainHarvestTime).append("\n");
            tmpString.append("Current Stage : ").append(crop.getCurrentStage()).append("\n");
            tmpString.append("is Watered Today : ").append(crop.isWateredToday()).append("\n");
            tmpString.append("is fertilized Today : ").append(crop.isFertilizerToday()).append("\n");
        } else {
            return new Result(false, "you haven't planted in this tile!");
        }
        return new  Result(true, tmpString.toString());
    }

    public static Result manageFertilize(Matcher matcher) {
        String fertilizer = matcher.group(1);
        String direction = matcher.group(2);
        Direction dir;
        Item item = getItemFromString(fertilizer);
        if (item == null) {
            return new Result(false, "this Fertilizer does not exist!");
        } else if ((dir = getDirectionFromString(direction)) == null) {
            return new Result(false, "this direction does not exist!");
        }
        Position position = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPosition();
        int x = position.getX();
        int y = position.getY();
        switch (dir) {
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            case UPLEFT:
                x -= 1;
                y -= 1;
                break;
            case UPRIGHT:
                x += 1;
                y -= 1;
                break;
            case DOWNLEFT:
                x -= 1;
                y += 1;
                break;
            case DOWNRIGHT:
                x += 1;
                y += 1;
                break;
            default:
                break;
        }
        if ((item.getName().equals(EtcType.SPEED_GRO.name()) || item.getName().equals(EtcType.DELUXE_SPEED_GRO.getName()))
                && (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getTileType().equals(TileType.WaterPlowedSoil)
                        || App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getTileType().equals(TileType.PlowedSoil))) {
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).setTileType(TileType.Speed_Gro);
            GameObject object = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject();
            if(object instanceof Tree) {
                ((Tree)App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject()).setFertilizerToday(true);
                ((Tree)App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject()).setSpeedGro(true);
            } else if(object instanceof Crop) {
                ((Crop)App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject()).setFertilizerToday(true);
                ((Crop)App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject()).setSpeedGro(true);
            }

        } else if ((item.getName().equals(EtcType.DELUXE_RETAINING_SOIL.name())||item.getName().equals(EtcType.BASIC_RETAINING_SOIL.name()) || (item.getName().equals(EtcType.QUALITY_RETAINING_SOIL.name())))
                && (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getTileType().equals(TileType.WaterPlowedSoil)
                || App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getTileType().equals(TileType.PlowedSoil))){
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).setTileType(TileType.Deluxe_Retaining_Soil);
            GameObject object = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject();
            if(object instanceof Tree) {
                ((Tree)App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject()).setFertilizerToday(true);
                ((Tree)App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject()).setDeluxeRetainingSoil(true);
            } else if(object instanceof Crop) {
                ((Crop)App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject()).setFertilizerToday(true);
                ((Crop)App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x,y).getFixedObject()).setDeluxeRetainingSoil(true);
            }
        }


        return new Result(true,"You fertilized!");
    }

    public static void managePlaceRandomCropOrSeed(Farm farm){
        Random random = new Random();
        Seasons currentSeason = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason();

        int randomSeed = random.nextInt(SeedType.values().length);
        SeedType seedType = SeedType.values()[randomSeed];
        while (!seedType.name.contains("Seeds") || !containsSeason(seedType.season,currentSeason )) {
            randomSeed = random.nextInt(SeedType.values().length);
            seedType = SeedType.values()[randomSeed];
        }

        int randomCrop = random.nextInt(ForagingCropType.values().length);
        ForagingCropType type = ForagingCropType.values()[randomCrop];
        while (containsSeason(type.season,currentSeason)) {
            randomCrop = random.nextInt(ForagingCropType.values().length);
            type = ForagingCropType.values()[randomCrop];
        }

        int cropOrSeed = random.nextInt(2);

        for (int i = 0; i < farm.getTiles().length; i++) {
            for (int j = 0; j < farm.getTiles()[i].length; j++) {
                Tile tile = farm.getTiles()[i][j];
                if (tile.getFixedObject() == null && random.nextInt(100) == 0 && tile.isWalkable()) {
                    Position pos = new Position(i, j);
                    if (cropOrSeed == 0) {
                        tile.setFixedObject(new ForagingCrop(true, pos, type));
                    } else {
                        TileType tt = tile.getTileType();
                        if (tt == TileType.WaterPlowedSoil || tt == TileType.PlowedSoil
                                || tt == TileType.Speed_Gro || tt == TileType.Deluxe_Retaining_Soil) {
                            if (seedType.cropType instanceof CropType) {
                                tile.setFixedObject(new Crop(true, pos, (CropType) seedType.cropType));
                            } else if (seedType.cropType instanceof TreeType) {
                                tile.setFixedObject(new Tree( (TreeType) seedType.cropType, pos));
                            }
                        }
                    }
                }
            }
        }
    }

    public static Result howMuchWaterIsExist() {
        Item item = getItemFromString("Watering Can");
        if (item  == null) {
            return new  Result(false,"You don't have any water in this location!");
        }
        return new Result(true,"remaining water capacity :"+((Tool)App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().findItemByName("Watering Can")).getCapacity());
    }

    private static Direction getDirectionFromString(String input) {
        try {
            return Direction.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static SeedType getSeedTypeFromString(String input) {
        for (SeedType seedType1 : SeedType.values()) {
            if (input.equals(seedType1.name())) {
                return seedType1;
            }
        }
        return null;

    }
    private static Item getItemFromString(String input) {
        for(Slot slot : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().getSlots()){
            Item item = slot.getItem();
            if (item.getName().equals(input)) {
                return item;
            }
        }
        return null;
    }

    private static boolean containsSeason(Seasons[] seasons, Seasons season) {
        for(Seasons season1 : seasons){
            if(season.equals(season1)) return true;
        }
        return false;
    }


}
