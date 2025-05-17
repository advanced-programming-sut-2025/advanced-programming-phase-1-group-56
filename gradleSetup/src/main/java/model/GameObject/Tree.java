package model.GameObject;


import model.App;
import model.Enums.GameObjects.TreeType;
import model.Enums.WeatherAndTime.WeatherType;
import model.MapModule.Position;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

public class Tree extends GameObject implements TimeObserver {
    //    private final ArrayList<Integer> stages;
//    private final boolean recollectable;
//    private final int recollectDuration;
//    private final ArrayList<Seasons> seasons;
    private int daysWithNoWater;  //
    private int countCurrentStage; //
    private TreeType treeType;
    private int currentStage; //
    private boolean speedGro;
    private boolean deluxeRetainingSoil;
    //    private final HashMap<Item,Integer> itemsGiven;
    private boolean isWateredToday;
    private boolean isFertilizerToday;
    private boolean isHarvest;
    private boolean isProtected;
    private int harvestDayRegrowth;
    private boolean isComplete;

    public Tree (TreeType treeType, Position position)
    {
        super(false,position);
        this.treeType = treeType;
        this.isWateredToday = false;
        this.isFertilizerToday = false;
        this.currentStage = 0;
        this.isHarvest = false;
        this.isProtected = false;
        this.speedGro = false;
        this.deluxeRetainingSoil = false;
        this.countCurrentStage = 0;
        this.daysWithNoWater = 0;
        this.harvestDayRegrowth = 0;
        this.isComplete = false;
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

//    public ArrayList<Seasons> getSeasons() {
//        return treeType.;
//    }

//    public int getRecollectDuration() {
//        return recollectDuration;
//    }
//
//    public boolean isRecollectable() {
//        return recollectable;
//    }


    public TreeType getTreeType() {
        return treeType;
    }

    public boolean isWateredToday() {
        return isWateredToday;
    }

//    public ArrayList<Integer> getStages() {
//        return stages;
//    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

//    public HashMap<Item, Integer> getItemsGiven() {
//        return itemsGiven;
//    }
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
            if(!(treeType == TreeType.TREE_BARK || treeType == TreeType.BURNT_TREE ||  treeType == TreeType.NORMAL_TREE)) {
                if(!isWateredToday) {
                    daysWithNoWater++;
                }
                if(deluxeRetainingSoil){
                    daysWithNoWater = 0;
                }
                countCurrentStage++;
                harvestDayRegrowth++;
                int countCurrentStage1 = 7;
                if(speedGro){
                    countCurrentStage1--;
                }
                if (currentStage == 3 && countCurrentStage == countCurrentStage1 && isComplete == false) {
                    isComplete = true;
                    isHarvest = true;
                }
                if (harvestDayRegrowth == treeType.fruitHarvestCycle) {
                    isHarvest = true;
                }
                if (countCurrentStage == 7 && isComplete == false) {
                    this.currentStage++;
                }
                isWateredToday = false;
            }
        } else{
            if(isWateredToday) {
                daysWithNoWater = 0;
            }
            if(App.getCurrentUser().getCurrentGame().getWeatherState().getTodayWeather() != WeatherType.Sunny){
                this.isWateredToday = true;
            }
        }
    }

    public boolean isHarvest() {
        return isHarvest;
    }

    public void setHarvest(boolean harvest) {
        isHarvest = harvest;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public int getCountCurrentStage() {
        return countCurrentStage;
    }

    public void setCountCurrentStage(int countCurrentStage) {
        this.countCurrentStage = countCurrentStage;
    }

    public int getDaysWithNoWater() {
        return daysWithNoWater;
    }

    public void setDaysWithNoWater(int daysWithNoWater) {
        this.daysWithNoWater = daysWithNoWater;
    }

    public int getHarvestDayRegrowth() {
        return harvestDayRegrowth;
    }

    public void setHarvestDayRegrowth(int harvestDayRegrowth) {
        this.harvestDayRegrowth = harvestDayRegrowth;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
