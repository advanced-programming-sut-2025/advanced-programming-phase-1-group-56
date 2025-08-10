package io.src.model.Enums.Items;

import io.src.model.Enums.GameObjects.EtcObjectType;
import io.src.model.items.Etc;
import org.jetbrains.annotations.Nullable;

public enum EtcType implements ItemType {

    WOOD("Wood", 2, null,""),
    FIBER("Fiber", 1, null,""),
    GRASS_STARTER("Grass_Starter", 50, null,""),
    BASIC_FERTILIZER("Basic_Fertilizer", 2, null,""),
    QUALITY_FERTILIZER("Quality_Fertilizer", 10, null,""),
    SPEED_GRO("Speed_Gro", 20, null,"Speed-Gro"),
    DELUXE_SPEED_GRO("Deluxe_Speed_Gro", 40, null,"Deluxe_Speed-Gro"),
    EGG("Egg", 50, null,""),
    BIG_EGG("Big_Egg", 50, null,"Large_Egg"),
    DUCK_EGG("Duck_Egg", 95, null,""),
    DUCK_FEATHER("Duck_Feather", 250, null,""),
    WOOL("Wool", 340, null,""),
    RABBITS_FOOT("Rabbit_Foot", 565, null,""),
    DINOSAUR_EGG("Dinosaur_Egg", 350, null,""),
    MILK("Milk", 125, null,"Large_Milk"),
    BIG_MILK("Big_Milk", 190, null,""),
    GOAT_MILK("Goat_Milk", 225, null,""),
    BIG_GOAT_MILK("Big_Goat_Milk", 345, null,"Large_Goat_Milk"),
    TRUFFLE("Truffle", 625, null,""),
    HAY("Hay", 0, null,"Wheat"),
    COPPER_BAR("Copper_Bar", 60, null,""),
    IRON_BAR("Iron_Bar", 120, null,""),
    GOLD_BAR("Gold_Bar", 250, null,""),
    IRIDIUM_BAR("Iridium_Bar", 1000, null,""),
    BOUQUET("Bouquet", 100, null,""),
    WEDDING_RING("Wedding_Ring", 1000, null,""),
    BASIC_RETAINING_SOIL("Basic_Retaining_Soil", 4, null,""),
    QUALITY_RETAINING_SOIL("Quality_Retaining_Soil", 5, null,""),
    DELUXE_RETAINING_SOIL("Deluxe_Retaining_Soil", 5, null,""),
    CLOTH("Cloth", 100, null,""),
    CHERRY_BOMB("Cherry_Bomb", 50, null,""),
    BOMB("Bomb", 50, null,""),
    MEGA_BOMB("Mega_Bomb", 50, null,""),
    SPRINKLER("Sprinkler", 0, EtcObjectType.SPRINKLER,""),
    QUALITY_SPRINKLER("Quality_Sprinkler", 0, EtcObjectType.QUALITY_SPRINKLER,""),
    SCARE_CROW("Scare_Crow", 0, EtcObjectType.SCARE_CROW,""),
    DELUXE_SCARE_CROW("Deluxe_Scarecrow", 0, EtcObjectType.DELUXE_SCARE_CROW,""),
    IRIDIUM_SPRINKLER("Iridium_Sprinkler", 0, EtcObjectType.IRIDIUM_SPRINKLER,""),
    Money("Money", 1, null,""),
    NPC_FRIENDSHIP_XP("npc friendship xp", 1, null,""),
    ANY_PLANT("Any_Plant", -1, null,"");


    final public String name;
    final public int value;
    final public EtcObjectType etcObjectType;
    private final String assetName;

    EtcType(String name, int value, EtcObjectType etcObjectType, String assetName) {
        this.name = name;
        this.value = value;
        this.etcObjectType = etcObjectType;
        this.assetName = assetName;
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


    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name;
            default -> assetName;
        };
    }

}
