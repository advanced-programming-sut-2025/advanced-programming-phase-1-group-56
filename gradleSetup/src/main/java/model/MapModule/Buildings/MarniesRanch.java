package model.MapModule.Buildings;

import model.MapModule.Position;

public class MarniesRanch extends Store {

    public MarniesRanch(boolean walkable, String name, Position doorPosition, Position startingPosition, int height, int width) {
        super(walkable, name, doorPosition, startingPosition, height, width);
    }

    @Override
    public void interact() {

    }
}
