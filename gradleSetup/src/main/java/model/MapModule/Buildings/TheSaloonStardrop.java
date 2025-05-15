package model.MapModule.Buildings;

import model.Enums.Stores.StardropSaloonProducts;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Position;
import model.TimeSystem.DateTime;

import java.util.ArrayList;

public class TheSaloonStardrop extends Store {

    public TheSaloonStardrop( Position startingPosition, boolean walkable, String name,Position doorPosition,int height, int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
        dailyProductList = StardropSaloonProducts.getProducts(StardropSaloonProducts.class);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(newDay) {
            dailyProductList = StardropSaloonProducts.getProducts(StardropSaloonProducts.class);
        }
    }

}
