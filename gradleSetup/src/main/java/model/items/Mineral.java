package model.items;

import model.Enums.Items.MineralItemType;

public class Mineral extends Item {
    private MineralItemType type;
    public Mineral(MineralItemType type ,int maxStackSize, boolean Stackable) {
        super(type.name, maxStackSize, Stackable);
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
