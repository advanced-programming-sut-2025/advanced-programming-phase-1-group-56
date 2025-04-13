package model.Enums.Animals;
import model.Enums.Items.ToolMaterial;
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
