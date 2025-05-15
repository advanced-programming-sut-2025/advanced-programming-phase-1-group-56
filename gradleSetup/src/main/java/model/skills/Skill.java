package model.skills;

import model.Enums.Skills;

public abstract class Skill {
    private int xp;
    private Skills name;
    private int level;


    public Skill(Skills name ,int xp) {
        this.name = name;
        this.xp = xp;
        this.level = 0;
    }

    public Skills getName(){
        return name;
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;

        if(xp > (100 * level+1 )+50 && level <4){
            this.level = level+1;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
