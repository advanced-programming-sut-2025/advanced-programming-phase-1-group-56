package model.GameObject;
import model.Enums.WeatherAndTime.Seasons;
import model.Locations.Position;
import java.util.ArrayList;

public class Plant extends GameObject {
    private final ArrayList<Integer> stages;
    private final boolean recollectable;
    private final int recollectDuration;
    private final ArrayList<Seasons> seasons;
    private final boolean CanBecomeGiant;
    private int currentStage;

    public Plant (Position position, int width, int height, ArrayList<Integer> stages, boolean recollectable, int recollectDuration, ArrayList<Seasons> seasons, boolean canBecomeGiant, boolean walkable[][])
    {
        super(position,width,height, walkable);
        this.stages = stages;
        this.recollectable = recollectable;
        this.recollectDuration = recollectDuration;
        this.seasons = seasons;
        CanBecomeGiant = canBecomeGiant;
    }


    public boolean isCanBecomeGiant() {
        return CanBecomeGiant;
    }

    public ArrayList<Seasons> getSeasons() {
        return seasons;
    }

    public int getRecollectDuration() {
        return recollectDuration;
    }

    public boolean isRecollectable() {
        return recollectable;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }
}