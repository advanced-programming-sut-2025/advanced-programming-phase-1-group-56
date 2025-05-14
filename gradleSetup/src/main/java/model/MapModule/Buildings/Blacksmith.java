package model.MapModule.Buildings;

import model.MapModule.Position;

public class Blacksmith extends Store {
    public Blacksmith( Position startingPosition,boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition,walkable, name, doorPosition, height, width);
    }

    @Override
    public void interact() {

    }
}
