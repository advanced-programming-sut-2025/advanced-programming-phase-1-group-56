package io.src.model.MapModule.Buildings;

import io.src.model.App;
import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.Enums.TileType;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;

public class GreenHouse extends Building implements TimeObserver {
    final static int wid = 11;
    final static int het = 11;
    private boolean broken = true;
    private GameLocation indoor;

    public GreenHouse(Position startingPosition,boolean walkable,String name,Position doorPosition,int height,int width) {
        super(startingPosition,walkable,name,doorPosition,het,wid,BuildingType.GREEN_HOUSE);
        GameLocation indoor = new GameLocation();
        Tile[][] tiles = new Tile[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                tiles[i][j] = new Tile(new Position(j,i),true, TileType.Soil);
            }
        }
        tiles[10][5].setTileType(TileType.Wrapper);
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

    @Override
    public String getAssetName() {
        return "Green_House" + ((broken)?"_Broken":"_Built");
    }

    //TODO add tiles and field to this after adding items
}
