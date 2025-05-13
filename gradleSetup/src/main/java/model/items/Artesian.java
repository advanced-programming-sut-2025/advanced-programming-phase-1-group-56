package model.items;

import model.Enums.Items.ArtisanMachineItemType;

public class Artesian extends Item {
    private ArtisanMachineItemType artisanMachineItemType;

    public Artesian(ArtisanMachineItemType itemType,int maxStackSize, boolean Stackable) {
        super(itemType.name, maxStackSize, Stackable);
        this.artisanMachineItemType = itemType;
    }


    public ArtisanMachineItemType getArtisanMachineItemType() {
        return artisanMachineItemType;
    }

    @Override
    public int getPrice(){
        return -1 ;
    }
}
