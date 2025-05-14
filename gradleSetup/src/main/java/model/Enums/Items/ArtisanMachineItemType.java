package model.Enums.Items;

public enum ArtisanMachineItemType implements ItemType {

    CHARCOAL_KLIN("Charcoal Klin",true, true),
    FURNACE("Furnace",true, true),
    SCARE_CROW("Scare Crow",false, true),
    DELUXE_SCARE_CROW("Deluxe Scarecrow",false, true),
    BEE_HOUSE("Bee House",true, true),
    CHEESE_PRESS("Cheese Press",true, true),
    KEG("Keg",true, true),
    LOOM("Loom",true, true),
    MAYONNAISE_MACHINE("Mayonnaise Machine",true, true),
    OIL_MAKER("Oil Maker",true, true),
    PRESERVES_JAR("Preserves Jar",true, true),
    DEHYDRATOR("Dehydrator",true, true),
    FISH_SMOKER("Fish Smoker",true, true);

    public final String name;
    public final boolean isArtisanBlock;
    public final boolean isPlacable;

    ArtisanMachineItemType(String name, boolean isArtisanBlock, boolean isPlacable) {
        this.name = name;
        this.isArtisanBlock = isArtisanBlock;
        this.isPlacable = isPlacable;
    }
}
