package model.Enums;

public enum BuffType {
    FarmingBuff("Farming",5 , 1),
    MiningBuff("Mining",5 , 1),
    FishingBuff5("Farming",5 , 1),
    FishingBuff10("Fishing",10 , 1),
    ForagingBuff5("Foraging",5 , 1),
    ForagingBuff11("Foraging",11 , 1),
    MaxEnergy50("Max Energy",3 , 50),
    MaxEnergy100("Max Energy",5 , 100),
    Depression("Depression",91, 2);


    private int duration;
    private int value;
    private String name;

    BuffType(String name ,int duration , int value) {
        this.name = name;
        this.duration = duration;
        this.value = value;
    }
    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name+"_"+value;
    }
}
