package io.src.model;

import io.src.model.Enums.BuffType;
import io.src.model.Enums.Skills;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;

public class Buff implements TimeObserver {
    private BuffType buffType;
    private int remainingTime;

    public Buff(BuffType buffType) {
        this.buffType = buffType;
        this.remainingTime = buffType.getDuration();
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(!newDay){
            remainingTime = remainingTime - 1;
        }
    }

    public void manageBuff(Player player){
        if(buffType == BuffType.FarmingBuff){
            player.getSkillByName(Skills.Farming.toString()).setLevel(player.getSkillByName(Skills.Farming.toString()).getLevel()+1);
        } else  if(buffType == BuffType.FishingBuff5){
            player.getSkillByName(Skills.Fishing.toString()).setLevel(player.getSkillByName(Skills.Fishing.toString()).getLevel()+1);
        } else if(buffType == BuffType.FishingBuff10){
            player.getSkillByName(Skills.Fishing.toString()).setLevel(player.getSkillByName(Skills.Fishing.toString()).getLevel()+1);
        } else if(buffType == BuffType.ForagingBuff5){
            player.getSkillByName(Skills.Foraging.toString()).setLevel(player.getSkillByName(Skills.Foraging.toString()).getLevel()+1);
        } else if(buffType == BuffType.ForagingBuff11){
            player.getSkillByName(Skills.Foraging.toString()).setLevel(player.getSkillByName(Skills.Foraging.toString()).getLevel()+1);
        } else if(buffType == BuffType.MiningBuff){
            player.getSkillByName(Skills.Mining.toString()).setLevel(player.getSkillByName(Skills.Mining.toString()).getLevel()+1);
        } else if(buffType == BuffType.MaxEnergy50){
            player.getEnergy().setMaxEnergy(player.getEnergy().getMaxEnergy()+50);
        } else if(buffType == BuffType.MaxEnergy100){
            player.getEnergy().setMaxEnergy(player.getEnergy().getMaxEnergy()+100);
        } else if(buffType == BuffType.Depression){
            player.getEnergy().setMaxEnergy(player.getEnergy().getMaxEnergy()/2);
        }
    }

    public void manageBuff1(Player player){
        if(buffType == BuffType.FarmingBuff){
            player.getSkillByName(Skills.Farming.toString()).setLevel(player.getSkillByName(Skills.Farming.toString()).getLevel()-1);
        } else  if(buffType == BuffType.FishingBuff5){
            player.getSkillByName(Skills.Fishing.toString()).setLevel(player.getSkillByName(Skills.Fishing.toString()).getLevel()-1);
        } else if(buffType == BuffType.FishingBuff10){
            player.getSkillByName(Skills.Fishing.toString()).setLevel(player.getSkillByName(Skills.Fishing.toString()).getLevel()-1);
        } else if(buffType == BuffType.ForagingBuff5){
            player.getSkillByName(Skills.Foraging.toString()).setLevel(player.getSkillByName(Skills.Foraging.toString()).getLevel()-1);
        } else if(buffType == BuffType.ForagingBuff11){
            player.getSkillByName(Skills.Foraging.toString()).setLevel(player.getSkillByName(Skills.Foraging.toString()).getLevel()-1);
        } else if(buffType == BuffType.MiningBuff){
            player.getSkillByName(Skills.Mining.toString()).setLevel(player.getSkillByName(Skills.Mining.toString()).getLevel()-1);
        } else if(buffType == BuffType.MaxEnergy50){
            player.getEnergy().setMaxEnergy(player.getEnergy().getMaxEnergy()-50);
        } else if(buffType == BuffType.MaxEnergy100){
            player.getEnergy().setMaxEnergy(player.getEnergy().getMaxEnergy()-100);
        } else if(buffType == BuffType.Depression){
            player.getEnergy().setMaxEnergy(player.getEnergy().getMaxEnergy()*2);
        }
    }
    public BuffType getBuffType() {
        return buffType;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    @Override
    public String toString() {
        return "[buff: "  + buffType.toString() + "-> duration" +remainingTime + "/" +this.buffType.getDuration() + " ]";
    }
}
