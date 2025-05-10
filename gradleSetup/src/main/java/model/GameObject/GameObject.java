package model.GameObject;

import model.MapModule.Position;

public abstract class GameObject {
    protected boolean walkable;

    public GameObject(boolean walkable) {
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
//    public Position getPosition() {
//        return position;
//    }
//
//    public void setPosition(Position position) {
//        this.position = position;
//    }

}
