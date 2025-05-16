package model.GameObject;

import model.App;
import model.Enums.GameObjects.ArtisanMachineType;
import model.Enums.GameObjects.CropType;
import model.Enums.Items.ArtisanGoodType;
import model.MapModule.Position;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;
import model.items.ArtisanGood;

public class ArtesianMachine extends GameObject implements TimeObserver {
    private ArtisanMachineType artisanMachineType;
    private ArtisanGoodType artisanGoodType;
    private ArtisanGood artisanGood;
    private int processTime;

    public ArtesianMachine(boolean walkable, Position position, ArtisanMachineType artisanMachineType) {
        super(walkable, position);
        this.artisanMachineType = artisanMachineType;
        this.artisanGood = null;
//        this.processTime = artisanGoodType.getProcessingTime();
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

    public void startMakeArtisanGood(ArtisanGoodType artisanGoodType) {
        this.artisanGoodType = artisanGoodType;
        this.processTime = artisanGoodType.getProcessingTime();
    }


    public void finishMakeArtisanGood(ArtisanGood artisanGood) {
//        this.artisanGood = new ArtisanGood(artisanGoodType , energy , sellPrice);
//        int processTime = artisanGoodType.getProcessingTime();
        this.artisanGood = artisanGood;
    }

    public ArtisanGood getArtisanGood() {
        return artisanGood;
    }


    public void setArtisanGood(ArtisanGood artisanGood) {
        this.artisanGood = artisanGood;
    }

    public ArtisanMachineType getArtisanMachineType() {
        return artisanMachineType;
    }


    public int getProcessTime() {
        return processTime;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }

    @Override

    public void onHourChanged(DateTime time, boolean newDay) {
        if(artisanGoodType == null || artisanGood != null)
            return;
        if(newDay){
            processTime-=11;
        }else{
            processTime--;
        }

        if(processTime == 0){
            finishMakeArtisanGood(new ArtisanGood(artisanGoodType));
            artisanGoodType = null;
        }
    }
}
