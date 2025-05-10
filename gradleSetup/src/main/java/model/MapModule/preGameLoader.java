package model.MapModule;

import com.google.gson.Gson;
import model.App;
import model.MapModule.GameLocations.Farm;

public class preGameLoader {
    public void creatFirstMap(){
        Farm farm1 = creatFirstFarm();
        Farm farm2 = creatFirstFarm();
        Farm farm3 = creatFirstFarm();
        Farm farm4 = creatFirstFarm();
        App.getCurrentUser().getCurrentGame().getGameMap().getGameLocations().add(farm1);
        App.getCurrentUser().getCurrentGame().getGameMap().getGameLocations().add(farm2);
        App.getCurrentUser().getCurrentGame().getGameMap().getGameLocations().add(farm3);
        App.getCurrentUser().getCurrentGame().getGameMap().getGameLocations().add(farm4);
    }
    public Farm creatFirstFarm(){
        Tile[][] tiles;
        try {
            //tiles = TileLoader.loadTiles("tiledMap\\tiledProject\\hello2.tmj");
            tiles = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Farm farm = new Farm(tiles);
        return farm;

    }
}
