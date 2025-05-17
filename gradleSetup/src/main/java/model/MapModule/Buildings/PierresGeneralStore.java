package model.MapModule.Buildings;

import model.Enums.Stores.PierreGeneralStoreProducts;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Position;
import model.TimeSystem.DateTime;

import java.util.ArrayList;

public class PierresGeneralStore extends Store {
    private ArrayList<NpcProduct> dailyProductList;

    public PierresGeneralStore(Position startingPosition, boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition, walkable, name, doorPosition, height, width);
        dailyProductList = PierreGeneralStoreProducts.getProducts(PierreGeneralStoreProducts.class);
        setOpeningHour(9);
        setClosingHour(17);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        dailyProductList = PierreGeneralStoreProducts.getProducts(PierreGeneralStoreProducts.class);
    }

    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }

    public void setDailyProductList(ArrayList<NpcProduct> dailyProductList) {
        this.dailyProductList = dailyProductList;
    }
}
