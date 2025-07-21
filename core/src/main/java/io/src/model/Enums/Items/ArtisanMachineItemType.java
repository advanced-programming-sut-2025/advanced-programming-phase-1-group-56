package io.src.model.Enums.Items;

import io.src.model.Enums.GameObjects.ArtisanMachineType;
import org.jetbrains.annotations.Nullable;
public enum ArtisanMachineItemType implements ItemType {

    CHARCOAL_KLIN("Charcoal_Klin", ArtisanMachineType.CHARCOAL_KILN, true, true,""),
    FURNACE("Furnace", ArtisanMachineType.FURNACE, true, true,""),
    BEE_HOUSE("Bee_House", ArtisanMachineType.BEE_HOUSE, true, true,""),
    CHEESE_PRESS("Cheese_Press", ArtisanMachineType.CHEESE_PRESS, true, true,""),
    KEG("Keg", ArtisanMachineType.KEG, true, true,""),
    LOOM("Loom", ArtisanMachineType.LOOM, true, true,""),
    MAYONNAISE_MACHINE("Mayonnaise_Machine", ArtisanMachineType.MAYONNAISE_MACHINE, true, true,""),
    OIL_MAKER("Oil_Maker", ArtisanMachineType.OIL_MAKER, true, true,""),
    PRESERVES_JAR("Preserves_Jar", ArtisanMachineType.PRESERVES_JAR, true, true,""),
    DEHYDRATOR("Dehydrator", ArtisanMachineType.Dehydrator, true, true,""),
    FISH_SMOKER("Fish_Smoker", ArtisanMachineType.FISH_SMOKER, true, true,"");

    private final String name;
    private final String assetName;

    public ArtisanMachineType getArtisanMachineType() {
        return artisanMachineType;
    }

    public boolean isArtisanBlock() {
        return isArtisanBlock;
    }

    public boolean isPlacable() {
        return isPlacable;
    }

    private final ArtisanMachineType artisanMachineType;
    public final boolean isArtisanBlock;
    public final boolean isPlacable;

    ArtisanMachineItemType(String name, ArtisanMachineType artisanMachineType, boolean isArtisanBlock, boolean isPlacable, String assetName) {
        this.name = name;
        this.artisanMachineType = artisanMachineType;
        this.isArtisanBlock = isArtisanBlock;
        this.isPlacable = isPlacable;
        this.assetName = assetName;
    }

    @Override
    public String getName() {
        return name;
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
