package io.src.model.skills;

import io.src.model.Enums.SfxEnum;
import io.src.model.Enums.Skills;
import io.src.model.GameAudioManager;

public abstract class Skill {
    private int xp;
    private final Skills name;
    private int level;


    public Skill(Skills name, int xp) {
        this.name = name;
        this.xp = xp;
        this.level = 0;
    }

    public String getName() {
        return name.toString();
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;

        if (xp > (100 * level + 1) + 50 && level < 4) {
            GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_LEVELUP.getPath(), false, GameAudioManager.sfxVolume);
            this.level = level + 1;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
