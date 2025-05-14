package model.items;

import model.Enums.Items.ArtisanMachineItemType;

public class Artesian extends Item {
    private ArtisanMachineItemType artisanMachineItemType;

    public Artesian(ArtisanMachineItemType itemType) {
        super(itemType.name,100, true,-1);
        this.artisanMachineItemType = itemType;
    }


    public ArtisanMachineItemType getArtisanMachineItemType() {
        return artisanMachineItemType;
    }

    public int getPrice(){
        return -1 ;
    }
}
