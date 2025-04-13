package model.locations;

import java.util.ArrayList;

public class Map {
    private ArrayList<Farm> farms;
    private Tile[][] tiles;


    public Map(ArrayList<Farm> farms) {
        this.farms = farms;
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public void setFarms(ArrayList<Farm> farms) {
        this.farms = farms;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }
}
