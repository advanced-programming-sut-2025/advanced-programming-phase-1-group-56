package model.items.CropAndSeed;

import model.GameObject.GameObject;
import model.MapModule.Tile;
import model.items.Item;

public class Seed extends Item implements Plantable {
    private final GameObject platingObject;


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
}
