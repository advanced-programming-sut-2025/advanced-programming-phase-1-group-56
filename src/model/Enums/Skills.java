package model.Enums;

import model.skills.*;

public enum Skills {

    Farming(new FarmingSkill()),
    Foraging(new ForagingSkill()),
    Fishing(new FishingSkill()),
    Mining(new MiningSkill());


    private Skill skill;

    Skills(Skill skill) {
        this.skill = skill;
    }


}
