package model.MapModule;

import model.Game;
import model.MapModule.GameLocations.Farm;
import model.MapModule.GameLocations.GameLocation;
import model.MapModule.GameLocations.Town;

import java.util.ArrayList;

public class GameMap{
    private Farm farm1;
    private Farm farm2;
    private Farm farm3;
    private Farm farm4;
    private Town pelikanTown;


    public Town getPelikanTown() {
        return pelikanTown;
    }

    public GameMap setPelikanTown(Town pelikanTown) {
        this.pelikanTown = pelikanTown;
        return this;
    }

    public Farm getFarm4() {
        return farm4;
    }

    public GameMap setFarm4(Farm farm4) {
        this.farm4 = farm4;
        return this;
    }

    public Farm getFarm3() {
        return farm3;
    }

    public GameMap setFarm3(Farm farm3) {
        this.farm3 = farm3;
        return this;
    }

    public Farm getFarm2() {
        return farm2;
    }

    public GameMap setFarm2(Farm farm2) {
        this.farm2 = farm2;
        return this;
    }

    public Farm getFarm1() {
        return farm1;
    }

    public GameMap setFarm1(Farm farm1) {
        this.farm1 = farm1;
        return this;
    }
}
