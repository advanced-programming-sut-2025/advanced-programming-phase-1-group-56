package io.src.model.Enums.GameObjects;

import io.src.model.App;
import io.src.model.Enums.WeatherAndTime.Seasons;
import org.jetbrains.annotations.Nullable;

public enum EtcObjectType {
    //    WOOD("Wood", 2),
//    FIBER("Fiber", 1),
//    GRASS_STARTER("Grass Starter", 50),
//    BASIC_FERTILIZER("Basic Fertilizer", 2),
//    QUALITY_FERTILIZER("Quality Fertilizer", 10),
//    SPEED_GRO("Speed Gro", 20),
//    DELUXE_SPEED_GRO("Deluxe Speed Gro", 40),
//    EGG("Egg", 50),
//    BIG_EGG("Big Egg", 50),
//    DUCK_EGG("Duck Egg", 95),
//    DUCK_FEATHER("Duck Feather", 250),
//    WOOL("Wool", 340),
//    RABBITS_FOOT("Rabbit's Foot", 565),
//    DINOSAUR_EGG("Dinosaur Egg", 350),
//    MILK("Milk", 125),
//    BIG_MILK("Big Milk", 190),
//    GOAT_MILK("Goat Milk", 225),
//    BIG_GOAT_MILK("Big Goat Milk", 345),
//    TRUFFLE("Truffle", 625),
//    HAY("Hay", 0),
//    COPPER_BAR("Copper Bar", 60),
//    IRON_BAR("Iron Bar", 120),
//    GOLD_BAR("Gold Bar", 250),
//    IRIDIUM_BAR("Iridium Bar", 1000),
//    BOUQUET("Bouquet", 100),
//    WEDDING_RING("Wedding Ring", 1000),
//    BASIC_RETAINING_SOIL("Basic Retaining Soil", 4),
//    QUALITY_RETAINING_SOIL("Quality Retaining Soil", 5),
//    DELUXE_RETAINING_SOIL("Deluxe Retaining Soil", 5),
//    CLOTH("Cloth", 100),
//    CHERRY_BOMB("Cherry Bomb", 50),
//    BOMB("Bomb", 50),
//    MEGA_BOMB("Mega Bomb", 50),
    SPRINKLER("Sprinkler", 0, ""),
    QUALITY_SPRINKLER("Quality Sprinkler", 0, ""),
    SCARE_CROW("Scare Crow", 0, ""),
    DELUXE_SCARE_CROW("Deluxe Scarecrow", 0, ""),
    IRIDIUM_SPRINKLER("Iridium Sprinkler", 0, ""),

    VANITY_TREE1("Vanity Tree1", 0, ""),
    VANITY_TREE2("Vanity Tree2" , 0 , ""),
    VANITY_TREE3("Vanity Tree1", 0, ""),
    PINKFU_TREE("Vanity PinkFu Tree", 0, ""),
    LANTERN("Vanity Tall Lantern", 0, ""),
    GRAVEKAG("Vanity Grave Stone Diagonal", 0, ""),
    GRAVEFROO("Vanity Grave Stone Short", 0, ""),
    GRAVESAF("Vanity Grave Stone Tall", 0, ""),
    JOJAMARTBILBOARD("JojaMart Bilboard", 0, ""),
    CANEX("Canex Vezarat Etelaat", 0, ""),
    CANEXDESK("Canex Desk", 0, ""),
    CANEXCHAIR("Canex Chair", 0, ""),
    BIGBUSH1("Vanity Big Green Bush1", 0, ""),
    BIGBUSH2("Vanity Big Bush2", 0, ""),
    SMALLBUSH1("Vanity Small Bush1", 0, ""),
    SMALLBUSH2("Vanity Small Bush2", 0, ""),
    FENCE11TOP("Vanity Fence area11 top", 0, ""),
    FENCE11LEFT("Vanity Fence area11 left", 0, ""),
    FENCE11RIGHT("Vanity Fence area11 right", 0, ""),
    BUILDING2("Vanity Building2", 0, ""),
    LIBRARY("Library", 0, ""),
    SORSORE("Vanity Taab Sorsore", 0, ""),
    ALAKOLANG("Vanity Bademjan Bird", 0, ""),
    VANITYMAILBOX("Vanity Mailbox", 0, ""),
    KUNDE("Vanity Kunde", 0, ""),
    BROWNPOT1("Vanity Brown Pot1", 0, ""),
    NARROWBUSH1("Vanity Narrow Tall Bush", 0, ""),
    FAVARE("Vanity Favare", 0, ""),
    VANITYSHOP1("Vanity Shop1", 0, ""),
    ;


    public final String name;
    public final int value;
    private final String assetName;


    EtcObjectType(String name, int value, String assetName) {
        this.name = name;
        this.value = value;
        this.assetName = assetName;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }

    public String getAssetNameBySeason() {
        Seasons s = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason();
        if (this == EtcObjectType.SPRINKLER || this == EtcObjectType.QUALITY_SPRINKLER ||this == EtcObjectType.SCARE_CROW ||this == EtcObjectType.DELUXE_SCARE_CROW ||this == EtcObjectType.IRIDIUM_SPRINKLER) {
            return getAssetName();
        }
        return getAssetName() + "_" + s.toString();
    }

}
