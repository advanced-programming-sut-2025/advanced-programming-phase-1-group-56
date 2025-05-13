package model.skills;

import model.Enums.Skills;

public abstract class Skill {
    private int xp;
    private Skills name;


    public Skill(Skills name ,int xp) {
        this.name = name;
        this.xp = xp;
    }

    public Skills getName(){
        return name;
    }
    public abstract int calculateLevel();

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
