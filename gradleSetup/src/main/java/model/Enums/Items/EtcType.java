package model.Enums.Items;

import model.Enums.GameObjects.ArtisanMachineType;
import model.Enums.GameObjects.EtcObjectType;

public enum EtcType implements ItemType {

    WOOD("Wood", 2 , EtcObjectType.WOOD),
    FIBER("Fiber", 1 , EtcObjectType.FIBER),
    GRASS_STARTER("Grass Starter", 50 , EtcObjectType.GRASS_STARTER),
    BASIC_FERTILIZER("Basic Fertilizer", 2 , EtcObjectType.BASIC_FERTILIZER),
    QUALITY_FERTILIZER("Quality Fertilizer", 10 , EtcObjectType.QUALITY_FERTILIZER),
    SPEED_GRO("Speed Gro", 20 , EtcObjectType.SPEED_GRO),
    DELUXE_SPEED_GRO("Deluxe Speed Gro", 40 , EtcObjectType.DELUXE_SPEED_GRO),
    EGG("Egg", 50 , EtcObjectType.EGG),
    BIG_EGG("Big Egg", 50 , EtcObjectType.BIG_EGG),
    DUCK_EGG("Duck Egg", 95 , EtcObjectType.DUCK_EGG),
    DUCK_FEATHER("Duck Feather", 250 , EtcObjectType.DUCK_FEATHER),
    WOOL("Wool", 340 , EtcObjectType.WOOL),
    RABBITS_FOOT("Rabbit's Foot", 565 , EtcObjectType.RABBITS_FOOT),
    DINOSAUR_EGG("Dinosaur Egg", 350 , EtcObjectType.DINOSAUR_EGG),
    MILK("Milk", 125 , EtcObjectType.MILK),
    BIG_MILK("Big Milk", 190 , EtcObjectType.BIG_MILK),
    GOAT_MILK("Goat Milk", 225 , EtcObjectType.GOAT_MILK),
    BIG_GOAT_MILK("Big Goat Milk", 345 , EtcObjectType.BIG_GOAT_MILK),
    TRUFFLE("Truffle", 625 , EtcObjectType.TRUFFLE),
    HAY("Hay", 0 , EtcObjectType.HAY),
    COPPER_BAR("Copper Bar", 60 , EtcObjectType.COPPER_BAR),
    IRON_BAR("Iron Bar", 120 , EtcObjectType.IRON_BAR),
    GOLD_BAR("Gold Bar", 250 , EtcObjectType.GOLD_BAR),
    IRIDIUM_BAR("Iridium Bar", 1000 , EtcObjectType.IRIDIUM_BAR),
    BOUQUET("Bouquet", 100 , EtcObjectType.BOUQUET),
    WEDDING_RING("Wedding Ring", 1000 , EtcObjectType.WEDDING_RING),
    BASIC_RETAINING_SOIL("Basic Retaining Soil", 4 , EtcObjectType.BASIC_RETAINING_SOIL),
    QUALITY_RETAINING_SOIL("Quality Retaining Soil", 5 , EtcObjectType.QUALITY_RETAINING_SOIL),
    DELUXE_RETAINING_SOIL("Deluxe Retaining Soil", 5 , EtcObjectType.DELUXE_RETAINING_SOIL),
    CLOTH("Cloth", 100 , EtcObjectType.CLOTH),
    CHERRY_BOMB("Cherry Bomb", 50 , EtcObjectType.CHERRY_BOMB),
    BOMB("Bomb", 50 , EtcObjectType.BOMB),
    MEGA_BOMB("Mega Bomb", 50 , EtcObjectType.MEGA_BOMB),
    SPRINKLER("Sprinkler", 0 , EtcObjectType.SPRINKLER),
    QUALITY_SPRINKLER("Quality Sprinkler", 0 , EtcObjectType.QUALITY_SPRINKLER),
    SCARE_CROW("Scare Crow", 0 , EtcObjectType.SCARE_CROW),
    DELUXE_SCARE_CROW("Deluxe Scarecrow", 0 , EtcObjectType.DELUXE_SCARE_CROW),
    IRIDIUM_SPRINKLER("Iridium Sprinkler", 0 , EtcObjectType.IRIDIUM_SPRINKLER);


    final public String name;
    final public int value;
    final public EtcObjectType etcObjectType;
//    final public boolean isArtisanBlock;
//    final public boolean isPlacable;

    EtcType(String name, int value , EtcObjectType etcObjectType/* boolean isArtisanBlock, boolean isPlacable*/) {
        this.name = name;
        this.value = value;
        this.etcObjectType = etcObjectType;
//        this.isArtisanBlock = isArtisanBlock;
//        this.isPlacable = isPlacable;
    }


}
