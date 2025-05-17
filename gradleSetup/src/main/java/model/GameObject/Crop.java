package model.GameObject;

import model.App;
import model.Enums.GameObjects.CropType;
import model.Enums.WeatherAndTime.WeatherType;
import model.MapModule.Position;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

public class Crop extends GameObject implements TimeObserver {
    private final CropType cropType;
    private int currentStage;
    //    private final HashMap<Item,Integer> itemsGiven;
    private boolean isWateredToday;
    private boolean isFertilizerToday;
    private boolean speedGro;
    private boolean deluxeRetainingSoil;
    private boolean isInGreenHouse;
    private boolean isProtected;
    private int daysWithNoWater;
    private int countCurrentStage ;
    private int harvestDayRegrowth;
    private boolean isHarvest;
    private boolean isComplete;
    private boolean is1time;



    public Crop(boolean walkable, Position position,  CropType cropType) {
        super(walkable, position);
        this.cropType = cropType;
        this.currentStage = 0;
        this.isWateredToday = false;
        this.isFertilizerToday = false;
        this.isInGreenHouse = false;
        this.isProtected = false;
        this.speedGro = false;
        this.deluxeRetainingSoil = false;
        this.countCurrentStage =0;
        this.daysWithNoWater = 0;
        this.harvestDayRegrowth = 0;
        this.isHarvest = false;
        this.isComplete = false;
        this.is1time = cropType.oneTime;
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }


    public CropType getCropType() {
        return cropType;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public boolean isWateredToday() {
        return isWateredToday;
    }

    public void setWateredToday(boolean wateredToday) {
        isWateredToday = wateredToday;
    }

    public boolean isFertilizerToday() {
        return isFertilizerToday;
    }

    public void setFertilizerToday(boolean fertilizerToday) {
        isFertilizerToday = fertilizerToday;
    }

    public boolean isSpeedGro() {
        return speedGro;
    }

    public void setSpeedGro(boolean speedGro) {
        this.speedGro = speedGro;
    }

    public boolean isDeluxeRetainingSoil() {
        return deluxeRetainingSoil;
    }

    public void setDeluxeRetainingSoil(boolean deluxeRetainingSoil) {
        this.deluxeRetainingSoil = deluxeRetainingSoil;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(newDay) {
            if(!isWateredToday) {
                daysWithNoWater++;
            }
            if(deluxeRetainingSoil){
                daysWithNoWater --;
            }
            countCurrentStage++;
            int countCurrentStage1 = cropType.stages[cropType.stages.length-1];
            if(speedGro){
                countCurrentStage1--;
            }
            isWateredToday = false;
            if(cropType.regrowthTime != -1){
                if (currentStage == cropType.stages.length-1 && countCurrentStage == countCurrentStage1 && !isComplete) {
                    isComplete = true;
                    isHarvest = true;
                }
                if (countCurrentStage == cropType.stages[currentStage] && !isComplete) {
                    this.currentStage++;
                }
                if (isComplete){
                    harvestDayRegrowth++;
                }
                if(harvestDayRegrowth == cropType.regrowthTime){
                    isHarvest = true;
                    harvestDayRegrowth = 0;
                }
            }else {
                if (currentStage == cropType.stages.length-1 && countCurrentStage == countCurrentStage1 && !isComplete) {
                    isComplete = true;
                    isHarvest = true;
                    is1time = true;
                }
                if (countCurrentStage == cropType.stages[currentStage] && !isComplete) {
                    this.currentStage++;
                }
            }
        }else{
            if(isWateredToday) {
                daysWithNoWater = 0;
            }
            if(App.getCurrentUser().getCurrentGame().getWeatherState().getTodayWeather() != WeatherType.Sunny && !isInGreenHouse){
                this.isWateredToday = true;
            }
        }
    }

    public boolean isInGreenHouse() {
        return isInGreenHouse;
    }

    public void setInGreenHouse(boolean inGreenHouse) {
        isInGreenHouse = inGreenHouse;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public int getDaysWithNoWater() {
        return daysWithNoWater;
    }

    public void setDaysWithNoWater(int daysWithNoWater) {
        this.daysWithNoWater = daysWithNoWater;
    }

    public int getCountCurrentStage() {
        return countCurrentStage;
    }

    public void setCountCurrentStage(int countCurrentStage) {
        this.countCurrentStage = countCurrentStage;
    }

    public boolean isHarvest() {
        return isHarvest;
    }

    public void setHarvest(boolean harvest) {
        isHarvest = harvest;
    }

    public boolean isIs1time() {
        return is1time;
    }

    public void setIs1time(boolean is1time) {
        this.is1time = is1time;
    }

    public void setHarvestDayRegrowth(int harvestDayRegrowth) {
        this.harvestDayRegrowth = harvestDayRegrowth;
    }
}
