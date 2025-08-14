package io.src.model.Enums.Items;

import org.jetbrains.annotations.Nullable;

public enum QualityPole implements ItemType {
    TrainingPole(0.1, "FishingPole_Training", ToolType.POLE_TRAINING),
    BambooPole(0.5, "FishingPole_Bamboo", ToolType.POLE_BAMBOO),
    FiberglassRod(0.9, "FishingPole_FiberGlass", ToolType.POLE_FIBERGLASS),
    IridiumRod(1.2, "FishingPole_Iridium", ToolType.POLE_IRIDIUM),
    AdvancedIridiumRod(2.5, "Advanced_Iridium_Rod", ToolType.Iridium_Trashcan),
    ;

    private final double factor;
    private final String assetName;
    private final ToolType toolType;

    QualityPole(double factor, String assetName, ToolType toolType) {
        this.factor = factor;
        this.assetName = assetName;
        this.toolType = toolType;
    }

    public double getCapacity() {
        return factor;
    }

    @Override
    public String getName() {
        return this.assetName;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> getName().replace(" ", "_");
            default -> assetName;
        };
    }


    public ToolType getToolType() {
        return toolType;
    }
}
