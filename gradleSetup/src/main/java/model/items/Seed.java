package model.items;

import model.Enums.Items.SeedType;

public class Seed extends Item {
    private SeedType seedType;

    public Seed( SeedType seedType,int maxStackSize, boolean Stackable) {
        super(seedType.name, maxStackSize, Stackable);
        this.seedType = seedType;
    }

    public SeedType getSeedType() {
        return seedType;
    }

    @Override
    public int getPrice() {
        return -1;
    }

}
