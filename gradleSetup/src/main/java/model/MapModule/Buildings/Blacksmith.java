package model.MapModule.Buildings;

import model.Enums.Stores.BlackSmithProducts;
import model.Enums.Stores.CarpenterShopProducts;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Position;
import model.TimeSystem.DateTime;

import java.util.ArrayList;


public class Blacksmith extends Store {
    private ArrayList<NpcProduct> dailyProductList;
    public Blacksmith( Position startingPosition,boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition,walkable, name, doorPosition, height, width);
        dailyProductList = BlackSmithProducts.getProducts(BlackSmithProducts.class);
        setOpeningHour(9);
        setClosingHour(16);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        dailyProductList = BlackSmithProducts.getProducts(BlackSmithProducts.class);

    }

}
