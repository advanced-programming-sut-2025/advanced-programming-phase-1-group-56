package model.MapModule.Buildings;

import model.Enums.Stores.JojamartProducts;
import model.Enums.Stores.MarniesRanchProducts;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Position;
import model.TimeSystem.DateTime;

import java.util.ArrayList;

public class JojaMart extends Store {
    private ArrayList<NpcProduct> dailyProductList;
    public JojaMart( Position startingPosition,boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
        dailyProductList = JojamartProducts.getProducts(JojamartProducts.class);
        setOpeningHour(9);
        setClosingHour(23);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        dailyProductList = JojamartProducts.getProducts(JojamartProducts.class);
    }
}
