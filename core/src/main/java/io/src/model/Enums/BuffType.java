package io.src.model.Enums;
import org.jetbrains.annotations.Nullable;

public enum BuffType {
    FarmingBuff("Farming", 5, 1, "Farming_Skill_Icon"),
    MiningBuff("Mining", 5, 1, "Mining_Skill_Icon"),
    FishingBuff5("Farming", 5, 1, "Farming_Skill_Icon"),
    FishingBuff10("Fishing", 10, 1, "Fishing_Skill_Icon"),
    ForagingBuff5("Foraging", 5, 1, "Foraging_Skill_Icon"),
    ForagingBuff11("Foraging", 11, 1, "Foraging_Skill_Icon"),
    MaxEnergy50("Max Energy", 3, 50, "Max_Energy_Buff"),
    MaxEnergy100("Max Energy", 5, 100, "Max_Energy_Buff"),
    Depression("Depression", 91, 2, "Depression_Buff"),
    ;


    private int duration;
    private int value;
    private String name;
    private String assetName;

    BuffType(String name, int duration, int value, String assetName) {
        this.name = name;
        this.duration = duration;
        this.value = value;
        this.assetName = assetName;
    }


    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "_" + value;
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
