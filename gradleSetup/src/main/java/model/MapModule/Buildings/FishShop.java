package model.MapModule.Buildings;

import model.Enums.Registery.StoreType;
import model.MapModule.Position;

public class FishShop extends Store {
    private final StoreType storeType = StoreType.FISH_SHOP;
    public FishShop( Position startingPosition,boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
    }

    @Override
    public void interact() {

    }

    public StoreType getStoreType() {
        return storeType;
    }
}
