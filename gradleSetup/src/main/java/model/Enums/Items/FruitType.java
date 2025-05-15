package model.Enums.Items;

import model.Enums.BuffType;

public enum FruitType implements ItemType {
    APRICOT("Apricot", 38, 59 ,null),
    CHERRY("Cherry", 38,80 , null),
    BANANA("Banana", 75,150 , null),
    MANGO("Mango", 100,130 , null),
    ORANGE("Orange", 38,100, null),
    PEACH("Peach", 38,140 , null),
    APPLE("Apple", 38,100 , null),
    POMEGRANATE("Pomegranate", 38,140 , null),
    OAK_RESIN("Oak Resin", 0,150 , null),
    MAPLE_SYRUP("Maple Syrup", 0,200 , null),
    PINE_TAR("Pine Tar", 0,100 , null),
    SAP("Sap", -2,2 , null),
    COMMON_MUSHROOM("Common Mushroom", 38,40 , null),
    MYSTIC_SYRUP("Mystic Syrup", 500, 1000 , null),
    ANY_FRUIT("Any Fruit", 38,100 , null),;


    private String name;
    private int energy;
    private int price;
    private BuffType buffType;


    FruitType(String name, int energy, int price, BuffType buffType){
        this.name = name;
        this.energy = energy;
        this.price = price;
        this.buffType = buffType;
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
}
