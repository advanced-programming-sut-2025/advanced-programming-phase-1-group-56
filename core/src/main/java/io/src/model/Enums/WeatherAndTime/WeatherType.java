package io.src.model.Enums.WeatherAndTime;

import org.jetbrains.annotations.Nullable;

public enum WeatherType {
    Sunny("Sunny_Icon"),
    Rainy("Rainy_Icon"),
    Storm("Storm_Icon"),
    Snow("Snowy_Icon"),;

    private final String assetName;

    WeatherType(String assetName) {
        this.assetName = assetName;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName){
            case ""->this.toString();
            case null->"null";
            default -> assetName;
        };
    }
}
