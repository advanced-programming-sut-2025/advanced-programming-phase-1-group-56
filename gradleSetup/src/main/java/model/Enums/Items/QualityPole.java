package model.Enums.Items;

public enum QualityPole implements ItemType {
    TrainingPole(0.1) ,
    BambooPole(0.5),
    FiberglassRod(0.9),
    IridiumRod(1.2);

    private final double factor;

    QualityPole(double factor) {
        this.factor = factor;

    }

    @Override
    public String getName() {
        return this.toString();
    }
    public double getCapacity() {
        return factor;
    }
}