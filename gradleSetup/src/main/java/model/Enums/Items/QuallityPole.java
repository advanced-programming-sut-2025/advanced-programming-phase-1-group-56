package model.Enums.Items;

public enum QuallityPole {
    TrainingPole(0.1) ,
    BambooPole(0.5),
    FiberglassRod(0.9),
    IridiumRod(1.2);

    private final double factor;

    QuallityPole(double factor) {
        this.factor = factor;

    }

    public double getCapacity() {
        return factor;
    }
}