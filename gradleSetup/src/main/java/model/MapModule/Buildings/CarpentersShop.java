package model.MapModule.Buildings;

import model.Enums.Buildings.BuildingType;
import model.Enums.Stores.CarpenterShopProducts;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Position;
import model.TimeSystem.DateTime;

import java.util.ArrayList;

public class CarpentersShop extends Store {
    @Override
    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }

    private ArrayList<NpcProduct> dailyProductList = new ArrayList<>();

    public CarpentersShop(Position startingPosition, boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition, walkable, name, doorPosition, height, width);
        dailyProductList.clear();
        for (CarpenterShopProducts pr : CarpenterShopProducts.values()) {
            dailyProductList.add(pr.getProduct());
        }
        setOpeningHour(9);
        setClosingHour(20);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        dailyProductList.clear();
        for (CarpenterShopProducts pr : CarpenterShopProducts.values()) {
            dailyProductList.add(pr.getProduct());
        }
    }

    public NpcProduct findBuildingByType(BuildingType buildingType) {
        for (NpcProduct product : dailyProductList) {
            if (product.getSaleable() instanceof BuildingType type) {
                if (type.equals(buildingType)) {
                    return product;
                }
            }
        }
        return null;
    }
}
