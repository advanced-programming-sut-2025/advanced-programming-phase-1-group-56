package model.items;

import model.Enums.Items.ArtisanGoodType;

public class ArtisanGood extends Item {
    private ArtisanGoodType artisanGoodType;
    private int energy;
    private int sellPrice;
    public ArtisanGood(ArtisanGoodType artesianGoodType, int energy , int sellPrice) {
        super(artesianGoodType.getName(), 100, true);
        this.energy = energy;
        this.sellPrice = sellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
    @Override
    public int getPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public ArtisanGoodType getArtisanGoodType() {
        return artisanGoodType;
    }


}
