package io.src.model.Enums.Animals;

public enum AnimalProductQuality {
    normal(1),
    silver(1.25),
    gold(1.5),
    Iridium(2);

    private final double factor;

    AnimalProductQuality(double factor) {
        this.factor = factor;

    }

    public double getCapacity() {
        return factor;
    }
}
