package io.src.model.States;

public class Energy {

    private double energy;
    private double maxEnergy = 200;
    private boolean unlimited;


    public Energy(double energy) {
        this.energy = energy;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public boolean isUnlimited() {
        return unlimited;
    }

    public void toggleUnlimited() {
        unlimited = !unlimited;
    }

    public double getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
        this.energy = Math.min(maxEnergy, energy);
    }
}
