package io.src.model.items;

import io.src.model.Enums.Items.ArtisanGoodType;

public class ArtisanGood extends Item {
    private ArtisanGoodType artisanGoodType;
//    private int energy;
//    private int sellPrice;

    public ArtisanGood(ArtisanGoodType artesianGoodType) {
        super(artesianGoodType.getName(), 9999, true, artesianGoodType.getSellPrice());
//        this.energy = energy;
//        this.sellPrice = sellPrice;
    }

    public int getEnergy() {
        return artisanGoodType.getEnergy();
    }

    public int getPrice() {
        return artisanGoodType.getSellPrice();
    }

    @Override
    public String getAssetName() {
        return artisanGoodType.getAssetName();
    }

//    public void setSellPrice(int sellPrice) {
//        this.sellPrice = sellPrice;
//    }

    public ArtisanGoodType getArtisanGoodType() {
        return artisanGoodType;
    }


}
