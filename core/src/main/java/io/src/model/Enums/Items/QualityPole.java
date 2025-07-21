package io.src.model.Enums.Items;

import org.jetbrains.annotations.Nullable;

public enum QualityPole implements ItemType {
    TrainingPole(0.1, "Training_Rod"),
    BambooPole(0.5, "Bamboo_Pole"),
    FiberglassRod(0.9, "Fiberglass_Rod"),
    IridiumRod(1.2, "Iridium_Rod"),
    AdvancedIridiumRod(2.5, "Advanced_Iridium_Rod"),
    ;

    private final double factor;
    private final String assetName;

    QualityPole(double factor, String assetName) {
        this.factor = factor;
        this.assetName = assetName;
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


}
