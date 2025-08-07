package io.src.model.Enums.WeatherAndTime;

import org.jetbrains.annotations.Nullable;

public enum Seasons {
    Spring("Spring_Icon"),
    Summer("Summer_Icon"),
    Fall("Fall_Icon"),
    Winter("Winter_Icon");

    private final String assetName;


    Seasons(String assetName) {
        this.assetName = assetName;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "" -> this.toString();
            case null -> "null";
            default -> assetName;
        };
    }
}
