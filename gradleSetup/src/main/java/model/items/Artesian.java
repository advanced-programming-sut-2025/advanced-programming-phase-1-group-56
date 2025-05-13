package model.items;

import model.Enums.Items.ArtisanMachineItemType;

public class Artesian extends Item {
    private ArtisanMachineItemType artisanMachineItemType;

    public Artesian(String name, int maxStackSize, boolean Stackable, ArtisanMachineItemType itemType) {
        super(name, maxStackSize, Stackable);
        this.artisanMachineItemType = itemType;
    }


    public ArtisanMachineItemType getArtisanMachineItemType() {
        return artisanMachineItemType;
    }
}
