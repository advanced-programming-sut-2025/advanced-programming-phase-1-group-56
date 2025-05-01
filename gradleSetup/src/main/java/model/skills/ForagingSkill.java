package model.skills;

public class ForagingSkill extends Skill {
    public ForagingSkill(int xp) {
        super(xp);
    }

    @Override
    public int calculateLevel() {
        return 0;
    }
}
