package io.src.model.GameObject;

import com.badlogic.gdx.math.Vector2;
import io.src.model.MapModule.Position;

public abstract class GameObject {
    //    public UUID objectId;
    protected boolean walkable;
    protected Position position;

    public GameObject(boolean walkable, Position position)
    {
//        this.objectId = UUID.randomUUID();
        this.position = position;
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }
//    protected Position position;
//    protected boolean [][] walkable;
//    protected int width;
//    protected int height;
//
//    public GameObject(Position position, int width, int height , boolean [][] walkable) {
//        this.position = position;
//        this.width = width;
//        this.height = height;
//        walkable = new boolean[width][height];
//        for (int i = 0; i < width; i++)
//            for (int j = 0; j < height; j++)
//                walkable[i][j] = false;
//    }
//
//    public void setTileWalkable(int dx, int dy, boolean value) {
//        if (dx >= 0 && dx < width && dy >= 0 && dy < height)
//            walkable[dx][dy] = value;
//    }
//
//    public boolean isTileWalkable(int dx, int dy) {
//        if (dx < 0 || dx >= width || dy < 0 || dy >= height) return false;
//        return walkable[dx][dy];
//    }
//
    public Position getPosition() {
        return position;
    }

    public Vector2 getPixelPosition() {
        float pX = position.getX() * 16;
        float pY = position.getY() * 16;
        return new Vector2(pX, pY);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract String getAssetName();
}
