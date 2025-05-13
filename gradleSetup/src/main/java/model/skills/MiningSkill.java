package model.skills;

import model.Enums.Skills;

public class MiningSkill extends Skill {
    public MiningSkill(Skills name , int xp) {

        super(name,xp);
    }

    @Override
    public int calculateLevel() {
        return 0;
    }
}
