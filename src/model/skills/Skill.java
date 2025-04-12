package model.skills;

public abstract class Skill {
    private int xp;

    public abstract int calculateLevel();

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
