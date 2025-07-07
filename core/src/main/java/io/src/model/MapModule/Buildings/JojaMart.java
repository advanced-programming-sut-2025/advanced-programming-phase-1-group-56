package io.src.model.MapModule.Buildings;

import io.src.model.Enums.Stores.FishShopProducts;
import io.src.model.Enums.Stores.JojamartProducts;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.Position;
import io.src.model.TimeSystem.DateTime;

import java.util.ArrayList;

public class JojaMart extends Store {
    private ArrayList<NpcProduct> dailyProductList;

    public JojaMart(Position startingPosition, boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition, walkable, name, doorPosition, height, width);
        dailyProductList = JojamartProducts.getProducts(JojamartProducts.class);
        setOpeningHour(9);
        setClosingHour(23);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            dailyProductList = JojamartProducts.getProducts(JojamartProducts.class);
        }
    }

    @Override
    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }

    @Override
    public void setDailyProductList(ArrayList<NpcProduct> dailyProductList) {
        this.dailyProductList = dailyProductList;
    }
}
