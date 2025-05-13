package model.States;

public class Energy {

    private int energy;
    private int maxEnergy =200;
    private boolean unlimited;


    public Energy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public boolean isUnlimited() {
        return unlimited;
    }
    public void toggleUnlimited() {
        unlimited = !unlimited;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }
}
