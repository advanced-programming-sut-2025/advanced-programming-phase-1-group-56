package controller.GameMenuController;

import controller.CommandController;
import model.Enums.BuffType;
import model.Enums.GameObjects.CropType;
import model.Enums.GameObjects.ForagingCropType;
import model.Enums.GameObjects.TreeType;
import model.Enums.Items.FruitType;
import model.Enums.Items.SeedType;
import model.Result;

import java.util.Arrays;
import java.util.regex.Matcher;

public class FarmingController extends CommandController {
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
                tmpString.append("Base Health: ").append(cropType.health).append("\n");
                tmpString.append("Season: ").append(cropType.season.toString()).append("\n");
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
                    tmpString.append("Fruit: ").append(treeType.fruit != null ? ((FruitType)treeType.fruit).getName() : "None").append("\n");
                    tmpString.append("Harvest Cycle: ").append(treeType.fruitHarvestCycle).append("\n");
                    tmpString.append("Fruit Base Sell Price: ").append(treeType.fruitBaseSellPrice).append("\n");
                    tmpString.append("Is Fruit Edible: ").append(treeType.isFruitEdible).append("\n");
                    tmpString.append("Fruit Energy: ").append(treeType.fruitEnergy).append("\n");
                    tmpString.append("Fruit Health: ").append(treeType.fruitHealth).append("\n");
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
                            tmpString.append("Season: ").append(foragingCropType.season).append("\n");
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

    public static Result manageTree(){

        return null;
    }
    public static Result manageBigCrop(){

        return null;
    }
    public static Result manageMixedSeed(){

        return null;
    }
    public static Result manageCrows(){

        return null;
    }
    public static Result managePlantSeed(){

        return null;
    }
    public static Result showPlant(){

        return null;
    }
    public static Result manageFertilize(){

        return null;
    }
    public static Result howMuchWaterIsExist(){

        return null;
    }



}
