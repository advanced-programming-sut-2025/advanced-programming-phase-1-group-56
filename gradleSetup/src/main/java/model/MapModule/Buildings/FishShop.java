package model.MapModule.Buildings;

import model.MapModule.Position;

public class FishShop extends Store {
    public FishShop(boolean walkable, String name, Position doorPosition, Position startingPosition, int height, int width) {
        super(walkable,name,doorPosition,startingPosition,height,width);
    }

    @Override
    public void interact() {

    }
}
