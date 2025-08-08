package io.src.model.MapModule.GameLocations;

import io.src.model.App;
import io.src.model.Enums.GameLocationType;
import io.src.model.GameObject.GameObject;
import io.src.model.MapModule.Network;
import io.src.model.MapModule.Node;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class GameLocation extends Network {
    protected Tile[][] tiles;


    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }

    public GameLocationType getType() {
        return type;
    }

    protected GameLocationType type;

    protected final ArrayList<GameObject> gameObjects = new ArrayList<>();

    public GameLocation(GameLocationType type) {
        this.type = type;
    }


    public Tile getTileByPosition(float x, float y) {
        return tiles[(int) y][(int) x];
    }


    public Tile getTileByPosition(Position pos) {
        return tiles[(int) pos.getY()][(int) pos.getX()];
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

    public boolean isWithinBounds(int x, int y, int width, int height) {
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


    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public ArrayList<GameObject> getCopyOfGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }
}
