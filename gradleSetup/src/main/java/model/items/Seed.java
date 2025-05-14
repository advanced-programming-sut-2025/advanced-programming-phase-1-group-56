package model.items;

import model.Enums.Items.SeedType;

public class Seed extends Item {
    private SeedType seedType;

    public Seed( SeedType seedType) {
        super(seedType.name, 100, true,-1);
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
