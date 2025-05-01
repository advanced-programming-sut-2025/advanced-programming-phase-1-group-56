package model.skills;

public class MiningSkill extends Skill {
    public MiningSkill(int xp) {
        super(xp);
    }

    @Override
    public int calculateLevel() {
        return 0;
    }
}
