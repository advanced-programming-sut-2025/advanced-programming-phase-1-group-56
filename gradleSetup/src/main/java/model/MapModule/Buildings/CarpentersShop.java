package model.MapModule.Buildings;

import model.MapModule.Position;

public class CarpentersShop extends Store{
    public CarpentersShop(boolean walkable, String name, Position doorPosition, Position startingPosition, int height, int width) {
        super(walkable,name,doorPosition,startingPosition,height,width);
    }

    @Override
    public void interact() {

    }
}
