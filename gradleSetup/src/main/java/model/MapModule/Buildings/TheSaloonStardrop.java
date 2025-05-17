package model.MapModule.Buildings;

import model.Enums.Stores.StardropSaloonProducts;
import model.MapModule.Position;
import model.TimeSystem.DateTime;

public class TheSaloonStardrop extends Store {

    public TheSaloonStardrop( Position startingPosition, boolean walkable, String name,Position doorPosition,int height, int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
        dailyProductList = StardropSaloonProducts.getProducts(StardropSaloonProducts.class);
        setOpeningHour(12);
        setClosingHour(24);
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
