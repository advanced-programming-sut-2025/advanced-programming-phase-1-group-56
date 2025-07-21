package io.src.model.Enums;

import org.jetbrains.annotations.Nullable;

public enum Skills {
    Farming("Farming_Skill_Icon"),
    Foraging("Foraging_Skill_Icon"),
    Fishing("Fishing_Skill_Icon"),
    Mining("Mining_Skill_Icon");
    private final String assetName;

    Skills(String assetName) {
        this.assetName = assetName;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> this.toString().replace(" ", "_");
            default -> assetName;
        };
    }

}


