package model.MapModule.Buildings;

import model.Enums.Registery.StoreType;
import model.MapModule.Position;

public class Blacksmith extends Building {
    private final StoreType storeType = StoreType.BLACKSMITH;
    public Blacksmith( Position startingPosition,boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition,walkable, name, doorPosition, height, width);
    }

    @Override
    public void interact() {

    }
}
