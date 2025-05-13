package model.items;

import model.Enums.Items.MineralItemType;

public class Mineral extends Item {
    private MineralItemType type;
    public Mineral(String name, int maxStackSize, boolean Stackable,MineralItemType type) {
        super(name, maxStackSize, Stackable);
        this.type = type;
    }


    public MineralItemType getType() {
        return type;
    }
}
