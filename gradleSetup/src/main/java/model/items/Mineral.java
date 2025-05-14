package model.items;

import model.Enums.Items.MineralItemType;

public class Mineral extends Item {
    private MineralItemType type;
    public Mineral(MineralItemType type) {
        super(type.name, 100, true, type.sellPrice);
        this.type = type;
    }


    public MineralItemType getType() {
        return type;
    }

    @Override
    public int getPrice() {
        return type.sellPrice;
    }
}
