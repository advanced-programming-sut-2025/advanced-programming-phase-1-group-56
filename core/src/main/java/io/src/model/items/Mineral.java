package io.src.model.items;

import io.src.model.Enums.Items.MineralItemType;

public class Mineral extends Item {
    private MineralItemType type;
    public Mineral(MineralItemType type) {
        super(type.name, 9999,true,type.sellPrice);
        this.type = type;
    }


    public MineralItemType getType() {
        return type;
    }

    public String description(){
        return type.description;
    }
}
