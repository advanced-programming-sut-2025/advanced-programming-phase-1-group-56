package io.src.model.MapModule.GameLocations;

import io.src.model.App;
import io.src.model.MapModule.Network;
import io.src.model.MapModule.Node;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class GameLocation extends Network {
//    ArrayList<Tile> tiles = new ArrayList<>();
    Tile[][] tiles;
//    ArrayList<Building> buildings;
//    ArrayList<NPC> NPcs = new ArrayList<>();////


    public Tile getTileByPosition(float x, float y){
        return tiles[(int)y][(int)x];
    }



    public Tile getTileByPosition(Position pos){
        return tiles[(int)pos.getY()][(int)pos.getX()];
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Tile[] tx : tiles) {
            nodes.addAll(Arrays.asList(tx));
        }
        return nodes;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public boolean isWithinBounds(int x, int y, int width,int height) {
        return x >= 0 && x + width <= tiles[0].length && y >= 0 && y + height <= tiles.length;
    }

    public boolean isPlantingLand() {
            boolean isFarm = this instanceof Farm;
            boolean isPlayerGreenhouse = this == App.getMe().getPlayerFarm().getGreenHouse().getIndoor();
            boolean isPartnerGreenhouse = false;
            if (App.getMe().getPartner() != null) {
                isPartnerGreenhouse = this == App.getMe().getPartner().getPlayerFarm().getGreenHouse().getIndoor();
            }
            return isFarm || isPlayerGreenhouse || isPartnerGreenhouse;

    }
}
