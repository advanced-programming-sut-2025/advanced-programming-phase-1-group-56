package model.MapModule.Buildings;

import model.MapModule.Position;

public class FishShop extends Store {
    public FishShop( Position startingPosition,boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
    }

    @Override
    public void interact() {

    }

}
