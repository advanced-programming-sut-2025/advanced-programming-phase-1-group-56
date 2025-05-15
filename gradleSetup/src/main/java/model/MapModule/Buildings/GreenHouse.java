package model.MapModule.Buildings;

import model.App;
import model.Enums.TileType;
import model.MapModule.GameLocations.GameLocation;
import model.MapModule.Position;
import model.MapModule.Tile;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

import java.util.Observer;

public class GreenHouse extends Building implements TimeObserver {
    final static int wid = 6;
    final static int het = 5;
    private boolean broken = true;
    private GameLocation indoor;

    public GreenHouse(Position startingPosition,boolean walkable,String name,Position doorPosition,int height,int width) {
        super(startingPosition,walkable,name,doorPosition,wid,het);
        GameLocation indoor = new GameLocation();
        Tile[][] tiles = new Tile[5][6];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                tiles[i][j] = new Tile(new Position(i,j),true, TileType.Soil);
            }
        }
        indoor.setTiles(tiles);
        this.indoor = indoor;
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

    @Override
    public void interact() {

    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    @Override
    public GameLocation getIndoor() {
        return indoor;
    }

    @Override
    public void setIndoor(GameLocation indoor) {
        this.indoor = indoor;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {

    }

    //TODO add tiles and field to this after adding items
}
