package io.src.model.Enums.Items;

import io.src.model.Enums.BuffType;
import org.jetbrains.annotations.Nullable;

public enum FruitType implements ItemType {
    APRICOT("Apricot", 38, 59 ,null,""),
    CHERRY("Cherry", 38,80 , null,""),
    BANANA("Banana", 75,150 , null,""),
    MANGO("Mango", 100,130 , null,""),
    ORANGE("Orange", 38,100, null,""),
    PEACH("Peach", 38,140 , null,""),
    APPLE("Apple", 38,100 , null,""),
    POMEGRANATE("Pomegranate", 38,140 , null,""),
    OAK_RESIN("Oak Resin", 0,150 , null,""),
    MAPLE_SYRUP("Maple Syrup", 0,200 , null,""),
    PINE_TAR("Pine Tar", 0,100 , null,""),
    SAP("Sap", -2,2 , null,""),
    COMMON_MUSHROOM("Common Mushroom", 38,40 , null,""),
    MYSTIC_SYRUP("Mystic Syrup", 500, 1000 , null,""),
    ANY_FRUIT("Any Fruit", 38,100 , null,"");


    private final String name;
    private final int energy;
    private final int price;
    private final BuffType buffType;
    private final String assetName;


    FruitType(String name, int energy, int price, BuffType buffType,String assetName) {
        this.name = name;
        this.energy = energy;
        this.price = price;
        this.buffType = buffType;
        this.assetName = assetName;
    }

    public String getName(){
        return name;
    }
    public int getEnergy(){
        return energy;
    }
    public int getPrice(){
        return price;
    }
    public BuffType getBuffType(){
        return buffType;
    }

    public static FruitType fromName(String name) {
        for (FruitType type : FruitType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ","_");
            default -> assetName;
        };
    }
}
