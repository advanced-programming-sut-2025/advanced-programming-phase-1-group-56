package model.Enums.Items;

import model.Enums.GameObjects.EtcObjectType;
import model.items.Etc;

public enum EtcType implements ItemType {

    WOOD("Wood", 2 , null),
    FIBER("Fiber", 1 ,null),
    GRASS_STARTER("Grass Starter", 50 , null),
    BASIC_FERTILIZER("Basic Fertilizer", 2 , null),
    QUALITY_FERTILIZER("Quality Fertilizer", 10 , null),
    SPEED_GRO("Speed Gro", 20 , null),
    DELUXE_SPEED_GRO("Deluxe Speed Gro", 40 , null),
    EGG("Egg", 50 , null),
    BIG_EGG("Big Egg", 50 , null),
    DUCK_EGG("Duck Egg", 95 , null),
    DUCK_FEATHER("Duck Feather", 250 , null),
    WOOL("Wool", 340 , null),
    RABBITS_FOOT("Rabbit's Foot", 565 , null),
    DINOSAUR_EGG("Dinosaur Egg", 350 , null),
    MILK("Milk", 125 , null),
    BIG_MILK("Big Milk", 190 , null),
    GOAT_MILK("Goat Milk", 225 , null),
    BIG_GOAT_MILK("Big Goat Milk", 345 , null),
    TRUFFLE("Truffle", 625 , null),
    HAY("Hay", 0 , null),
    COPPER_BAR("Copper Bar", 60 , null),
    IRON_BAR("Iron Bar", 120 , null),
    GOLD_BAR("Gold Bar", 250 , null),
    IRIDIUM_BAR("Iridium Bar", 1000 , null),
    BOUQUET("Bouquet", 100 , null),
    WEDDING_RING("Wedding Ring", 1000 , null),
    BASIC_RETAINING_SOIL("Basic Retaining Soil", 4 , null),
    QUALITY_RETAINING_SOIL("Quality Retaining Soil", 5 , null),
    DELUXE_RETAINING_SOIL("Deluxe Retaining Soil", 5 , null),
    CLOTH("Cloth", 100 , null),
    CHERRY_BOMB("Cherry Bomb", 50 , null),
    BOMB("Bomb", 50 , null),
    MEGA_BOMB("Mega Bomb", 50 , null),
    SPRINKLER("Sprinkler", 0 , EtcObjectType.SPRINKLER),
    QUALITY_SPRINKLER("Quality Sprinkler", 0 , EtcObjectType.QUALITY_SPRINKLER),
    SCARE_CROW("Scare Crow", 0 , EtcObjectType.SCARE_CROW),
    DELUXE_SCARE_CROW("Deluxe Scarecrow", 0 , EtcObjectType.DELUXE_SCARE_CROW),
    IRIDIUM_SPRINKLER("Iridium Sprinkler", 0 , EtcObjectType.IRIDIUM_SPRINKLER),
    Money("Money",1,null),
    NPC_FRIENDSHIP_XP("npc friendship xp",1,null),
    ANY_PLANT("Any Plant",-1,null);


    final public String name;
    final public int value;
    final public EtcObjectType etcObjectType;

    EtcType(String name, int value , EtcObjectType etcObjectType) {
        this.name = name;
        this.value = value;
        this.etcObjectType = etcObjectType;
    }


    @Override
    public String getName() {
        return name;
    }

    public static EtcType fromName(String name) {
        for (EtcType type : EtcType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}
