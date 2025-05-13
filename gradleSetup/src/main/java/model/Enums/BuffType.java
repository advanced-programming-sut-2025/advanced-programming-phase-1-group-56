package model.Enums;

public enum BuffType {
    FarmingBuff(5 , 1),
    MiningBuff(5 , 1),
    FishingBuff5(5 , 1),
    FishingBuff10(10 , 1),
    ForagingBuff5(5 , 1),
    ForagingBuff11(11 , 1),
    MaxEnergy50(3 , 50),
    MaxEnergy100(5 , 100),
    Depression(120 , 2);


    private int duration;
    private int value;

    BuffType(int duration , int value) {
        this.duration = duration;
        this.value = value;
    }

    public int getDuration() {
        return duration;
    }

    public int getValue() {
        return value;
    }

}
