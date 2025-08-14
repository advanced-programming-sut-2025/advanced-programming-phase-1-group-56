package io.src.model.GameObject.NPC;

import io.src.model.App;
import io.src.model.Enums.Animals.AnimalType;
import io.src.model.Enums.BackPackType;
import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.Enums.Items.*;
import io.src.model.Enums.Recepies.CraftingRecipesList;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.Enums.WeatherAndTime.Seasons;
import io.src.model.Slot;
import io.src.model.items.*;

import java.util.ArrayList;
import java.util.Arrays;

public class NpcProduct {
    private final String name;
    private final Saleable saleable;
    private final String description;
    private final int price;
    private final int outOfSeasonPrice;
    private final Seasons[] seasons;
    private final int dailyStock;
    private int remainingStock;
    private String assetName;

    public NpcProduct(String name, Saleable saleable, String description, int price, int outOfSeasonPrice, Seasons[] seasons, int dailyStock) {
        this.name = name;
        this.saleable = saleable;
        this.description = description;
        this.price = price;
        this.outOfSeasonPrice = outOfSeasonPrice;
        this.seasons = seasons;
        this.dailyStock = dailyStock;
        remainingStock = dailyStock;
    }

    public int getRemainingStock() {
        return remainingStock;
    }

    public void setRemainingStock(int remainingStock) {
        this.remainingStock = remainingStock;
    }

    public void changeRemainingStock(int amount) {
        this.remainingStock += amount;
    }

    public int getDailyStock() {
        return dailyStock;
    }

    public Seasons[] getSeasons() {
        return seasons;
    }

    public int getOutOfSeasonPrice() {
        return outOfSeasonPrice;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Saleable getSaleable() {
        return saleable;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String Find_AssetName() {
        if (this.saleable instanceof Item item) {
            return item.getAssetName();
        } else if (this.saleable instanceof FoodRecipesList rec) {
            return rec.getAssetName();
        } else if (this.saleable instanceof CraftingRecipesList rec) {
            return rec.getAssetName();
        } else if (this.saleable instanceof BackPackType backPackType) {
            return backPackType.getAssetName();
        } else if (this.saleable instanceof BuildingType buildingType) {
            return buildingType.getAssetName() + "_Closed";
        } else if (this.saleable instanceof AnimalType animalType) {
            return animalType.getAssetName();
        } else if (this.saleable instanceof ToolType toolType) {
            return toolType.getAssetName();
        } else if (this.saleable instanceof TrashcanType trashcanType) {
            return trashcanType.getAssetName();
        } else if (this.saleable instanceof QualityPole qualityPole) {
            return qualityPole.getAssetName();
        }
        return null;
    }

    public Slot[] Find_ItemNeeded() {
        if (this.saleable instanceof BuildingType buildingType) {
            int woodNeeded = buildingType.getWoodCount();
            int stoneNeeded = buildingType.getStoneCount();
            ArrayList<Slot> slots = new ArrayList<>();
            if (woodNeeded != 0) {
                slots.add(new Slot(new Etc(EtcType.WOOD), woodNeeded));
            }
            if (stoneNeeded != 0) {
                slots.add(new Slot(new Mineral(MineralItemType.STONE), stoneNeeded));
            }
            return slots.toArray(new Slot[0]);
        } else if (this.saleable instanceof ToolType toolType) {
            ToolType before = toolType.findBeforeToolType();
            if (before != null) {
                return new Slot[]{new Slot(new Tool(before), 1), new Slot(new Etc(toolType.getToolMaterial().getOre()), 5)};
            }
            return new Slot[]{new Slot(new Etc(toolType.getToolMaterial().getOre()), 5)};
        } else if (this.saleable instanceof TrashcanType trashcanType) {

            TrashcanType before = trashcanType.findBeforeTrashCanType();
            if (before != null) {
                //System.out.println("Kodam :" + trashcanType.getName() + "Beforam " + before.getName());
                return new Slot[]{new Slot(new Tool(before), 1), new Slot(new Etc(trashcanType.getMaterial().getOre()), 5)};
            }
            return new Slot[]{new Slot(new Etc(trashcanType.getMaterial().getOre()), 5)};
        }
        return null;
    }

    public int getFinalPrice() {
        boolean isSeason = Arrays.asList(seasons).contains(App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason());
        if (isSeason) {
            return price;
        } else {
            return outOfSeasonPrice == -1 ? price : outOfSeasonPrice;
        }
    }

    public boolean isShopProduct() {
        if (this.saleable instanceof Item item) {
            return true;
        } else if (this.saleable instanceof FoodRecipesList rec) {
            return true;
        } else if (this.saleable instanceof CraftingRecipesList rec) {
            return true;
        } else if (this.saleable instanceof BackPackType backPackType) {
            return true;
        } else if (this.saleable instanceof QualityPole qualityPole) {
            return true;
        } else if (this.saleable instanceof BuildingType buildingType) {
            return false;
        } else if (this.saleable instanceof AnimalType animalType) {
            return false;
        } else if (this.saleable instanceof ToolType toolType) {
            return false;
        } else if (this.saleable instanceof TrashcanType trashcanType) {
            return false;
        } else if (this.saleable instanceof EtcType etcType) {
            return false;
        }
        return true;
    }
}
