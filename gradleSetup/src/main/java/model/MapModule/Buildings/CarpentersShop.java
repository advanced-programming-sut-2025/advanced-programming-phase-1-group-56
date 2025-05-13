package model.MapModule.Buildings;

import model.Enums.Registery.StoreType;
import model.MapModule.Position;

public class CarpentersShop extends Store{
    private final StoreType storeType = StoreType.CARPENTER;
    public CarpentersShop(Position startingPosition,boolean walkable, String name, Position doorPosition,  int height, int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
    }

    @Override
    public void interact() {

    }

    public StoreType getStoreType() {
        return storeType;
    }
}
