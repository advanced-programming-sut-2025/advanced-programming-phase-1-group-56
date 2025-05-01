package model.skills;

public class FishingSkill extends Skill {
    public FishingSkill(int xp) {
        super(xp);
    }

    @Override
    public int calculateLevel() {
        return 0;
    }
}
