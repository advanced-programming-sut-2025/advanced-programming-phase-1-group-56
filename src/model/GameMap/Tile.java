package model.GameMap;

public class Tile {
    private boolean hasItem;
    private boolean isWalkable;
    private Position position;
    private Object object;
    private

    public Tile(boolean hasItem, boolean isWalkable, Position position, Object object) {
        this.hasItem = hasItem;
        this.isWalkable = isWalkable;
        this.position = position;
        this.object = object;
    }


    public boolean isHasItem() {
        return hasItem;
    }

    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public Position getPosition() {
        return position;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

}
