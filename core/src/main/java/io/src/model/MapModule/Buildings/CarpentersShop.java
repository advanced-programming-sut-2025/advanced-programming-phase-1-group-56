package io.src.model.MapModule.Buildings;

import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.Enums.Stores.CarpenterShopProducts;
import io.src.model.Enums.Stores.FishShopProducts;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.Position;
import io.src.model.TimeSystem.DateTime;

import java.util.ArrayList;

public class CarpentersShop extends Store {
    @Override
    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }

    private ArrayList<NpcProduct> dailyProductList = new ArrayList<>();

    public CarpentersShop(Position startingPosition, boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition, walkable, name, doorPosition, height, width);
        dailyProductList = CarpenterShopProducts.getProducts(CarpenterShopProducts.class);
        setOpeningHour(9);
        setClosingHour(20);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            dailyProductList = CarpenterShopProducts.getProducts(CarpenterShopProducts.class);
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
