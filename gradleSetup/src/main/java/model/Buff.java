package model;

import model.Enums.BuffType;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

public class Buff implements TimeObserver {
    private BuffType buffType;
    private int remainingTime;

    public Buff(BuffType buffType) {
        this.buffType = buffType;
        this.remainingTime = buffType.getDuration();
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {

    }

    public BuffType getBuffType() {
        return buffType;
    }

    public int getRemainingTime() {
        return remainingTime;
    }
}
