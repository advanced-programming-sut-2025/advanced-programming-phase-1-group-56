package model.Enums.Items;

public enum EtcType {

    WOOD("Wood", 2),
    FIBER("Fiber", 1),
    GRASS_STARTER("Grass Starter", 50),
    BASIC_FERTILIZER("Basic Fertilizer", 2),
    QUALITY_FERTILIZER("Quality Fertilizer", 10),
    SPEED_GRO("Speed Gro", 20),
    DELUXE_SPEED_GRO("Deluxe Speed Gro", 40),
    EGG("Egg", 50),
    BIG_EGG("Big Egg", 50),
    DUCK_EGG("Duck Egg", 95),
    DUCK_FEATHER("Duck Feather", 250),
    WOOL("Wool", 340),
    RABBITS_FOOT("Rabbit's Foot", 565),
    DINOSAUR_EGG("Dinosaur Egg", 350),
    MILK("Milk", 125),
    BIG_MILK("Big Milk", 190),
    GOAT_MILK("Goat Milk", 225),
    BIG_GOAT_MILK("Big Goat Milk", 345),
    TRUFFLE("Truffle", 625),
    HAY("Hay", 0),
    COPPER_BAR("Copper Bar", 60),
    IRON_BAR("Iron Bar", 120),
    GOLD_BAR("Gold Bar", 250),
    IRIDIUM_BAR("Iridium Bar", 1000),
    BOUQUET("Bouquet", 100),
    WEDDING_RING("Wedding Ring", 1000),
    BASIC_RETAINING_SOIL("Basic Retaining Soil", 4),
    QUALITY_RETAINING_SOIL("Quality Retaining Soil", 5),
    DELUXE_RETAINING_SOIL("Deluxe Retaining Soil", 5),
    CLOTH("Cloth", 100),
    CHERRY_BOMB("Cherry Bomb", 50),
    BOMB("Bomb", 50),
    MEGA_BOMB("Mega Bomb", 50),
    SPRINKLER("Sprinkler", 0),
    QUALITY_SPRINKLER("Quality Sprinkler", 0),
    IRIDIUM_SPRINKLER("Iridium Sprinkler", 0);


    final public String name;
    final public int value;
//    final public boolean isArtisanBlock;
//    final public boolean isPlacable;

    EtcType(String name, int value/* boolean isArtisanBlock, boolean isPlacable*/) {
        this.name = name;
        this.value = value;
//        this.isArtisanBlock = isArtisanBlock;
//        this.isPlacable = isPlacable;
    }


}
