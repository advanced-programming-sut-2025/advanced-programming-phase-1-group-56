package model.items;

import model.Enums.Items.SeedType;

public class Seed extends Item {
    private SeedType SeedType;

    public Seed(String name, int maxStackSize, boolean Stackable, SeedType SeedType) {
        super(name, maxStackSize, Stackable);
        this.SeedType = SeedType;
    }

    public  SeedType getSeedType() {
        return SeedType;
    }

}
