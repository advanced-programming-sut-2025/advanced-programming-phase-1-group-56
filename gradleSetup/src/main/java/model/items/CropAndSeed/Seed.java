package model.items.CropAndSeed;

import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.GameObject;
import model.Locations.Tile;
import model.items.Item;

public class Seed  extends Item implements Plantable {
    private final GameObject platingObject;
    private Seasons season;


    public Seed(String name, GameObject platingObject) {
        super(name,99,true);//TODO
        this.platingObject = platingObject;
    }

    @Override
    public void plant(Tile tile) {

    }

    public GameObject getPlatingObject() {
        return platingObject;
    }

    public Seasons getSeason() {
        return season;
    }

    public void setSeason(Seasons season) {
        this.season = season;
    }
}
