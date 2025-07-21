package io.src.model.Enums;

import io.src.model.items.Saleable;
import org.jetbrains.annotations.Nullable;

public enum BackPackType implements Saleable {
    DeluxeBackpack(Integer.MAX_VALUE, null, "36_Backpack"),
    BigBackpack(24, DeluxeBackpack, "Big_Backpack"),
    InitialBackpack(12, BigBackpack, "Backpack");

    private final int capacity;
    private final BackPackType next;
    private final String assetName;

    BackPackType(int capacity, BackPackType next, String assetName) {
        this.capacity = capacity;
        this.next = next;
        this.assetName = assetName;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    public BackPackType getNext() {
        return next;
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
