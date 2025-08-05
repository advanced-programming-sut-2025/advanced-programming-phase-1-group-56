package io.src.model.Enums.Items;

import org.jetbrains.annotations.Nullable;

public enum GrassType implements ItemType {
    NormalGrass("Grass", "Grass_bank_01"),
    FiberGrass("Fiber_Grass", "Fiber_Grass1");
    private final String name;
    private final String assetName;

    GrassType(String name, String assetName) {
        this.name = name;
        this.assetName = assetName;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }
}
