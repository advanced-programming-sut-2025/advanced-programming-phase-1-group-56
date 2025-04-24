package model.items.CropAndSeed;

import model.GameObject.GameObject;
import model.items.Item;

import java.util.ArrayList;

public class MixSeed extends Item {
    String mixName;
    private final ArrayList<Seed> seeds;

    public MixSeed(String name, GameObject platingObject, ArrayList<Seed> seeds) {
        super(name,99,true);
        this.seeds = seeds;
    }

    public ArrayList<Seed> getSeeds() {
        return seeds;
    }
}
