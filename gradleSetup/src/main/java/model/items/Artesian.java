package model.items;

import model.Enums.Items.ArtisanMachineItemType;

public class Artesian extends Item {
    private final ArtisanMachineItemType artisanMachineItemType;

    public Artesian(ArtisanMachineItemType itemType) {
        super(itemType.getName(),9999, true,-1);
        this.artisanMachineItemType = itemType;
    }


    public ArtisanMachineItemType getArtisanMachineItemType() {
        return artisanMachineItemType;
    }

    public int getPrice(){
        return -1 ;
    }
}
