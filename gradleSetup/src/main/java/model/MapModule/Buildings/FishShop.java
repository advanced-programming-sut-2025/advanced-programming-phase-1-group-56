package model.MapModule.Buildings;

import model.Enums.Stores.FishShopProducts;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Position;
import model.TimeSystem.DateTime;

import java.util.ArrayList;

public class FishShop extends Store {
    private ArrayList<NpcProduct> dailyProductList;
    public FishShop( Position startingPosition,boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
        dailyProductList = FishShopProducts.getProducts(FishShopProducts.class);
        setOpeningHour(9);
        setClosingHour(17);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        dailyProductList = FishShopProducts.getProducts(FishShopProducts.class);
    }

    @Override
    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }
}
