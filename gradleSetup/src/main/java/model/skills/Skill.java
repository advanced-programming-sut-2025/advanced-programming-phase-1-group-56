package model.skills;

public abstract class Skill {
    private int xp;
    private int level;


    public Skill(int xp) {
        this.xp = xp;
    }

    public abstract int calculateLevel();

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
