package model.items;

import model.Enums.Items.Ore;

public class OreItem extends Item{
    private Ore ore;
    public OreItem(Ore ore) {
        super(ore.getName(), 100 , true , ore.getPrice());
        this.ore = ore;
    }
    public Ore getOre() {
        return ore;
    }
}
