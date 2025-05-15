package model.Enums.Items;

import model.Enums.GameObjects.ArtisanMachineType;

public enum ArtisanMachineItemType implements ItemType {

    CHARCOAL_KLIN("Charcoal Klin", ArtisanMachineType.CHARCOAL_KILN ,true, true),
    FURNACE("Furnace", ArtisanMachineType.FURNACE ,true, true),
    BEE_HOUSE("Bee House", ArtisanMachineType.BEE_HOUSE ,true, true),
    CHEESE_PRESS("Cheese Press", ArtisanMachineType.CHEESE_PRESS ,true, true),
    KEG("Keg", ArtisanMachineType.KEG ,true, true),
    LOOM("Loom", ArtisanMachineType.LOOM ,true, true),
    MAYONNAISE_MACHINE("Mayonnaise Machine", ArtisanMachineType.MAYONNAISE_MACHINE ,true, true),
    OIL_MAKER("Oil Maker", ArtisanMachineType.OIL_MAKER ,true, true),
    PRESERVES_JAR("Preserves Jar", ArtisanMachineType.PRESERVES_JAR ,true, true),
    DEHYDRATOR("Dehydrator", ArtisanMachineType.Dehydrator ,true, true),
    FISH_SMOKER("Fish Smoker", ArtisanMachineType.FISH_SMOKER ,true, true);

    public final String name;
    public final ArtisanMachineType artisanMachineType;
    public final boolean isArtisanBlock;
    public final boolean isPlacable;

    ArtisanMachineItemType(String name, ArtisanMachineType artisanMachineType,boolean isArtisanBlock, boolean isPlacable) {
        this.name = name;
        this.artisanMachineType = artisanMachineType;
        this.isArtisanBlock = isArtisanBlock;
        this.isPlacable = isPlacable;
    }
}
