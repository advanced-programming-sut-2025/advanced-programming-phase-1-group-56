package model.MapModule.GameLocations;

import model.App;
import model.MapModule.Network;
import model.MapModule.Node;
import model.MapModule.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class GameLocation extends Network {
//    ArrayList<Tile> tiles = new ArrayList<>();
    Tile[][] tiles;
//    ArrayList<Building> buildings;
//    ArrayList<NPC> NPcs = new ArrayList<>();////


    public Tile getTileByPosition(int x, int y){
        for(Tile[] tx : tiles){
            for (Tile ty : tx){
                if(ty.getPosition().getX() == x && ty.getPosition().getY() == y)
                    return ty;
            }
        }
        return null;
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
