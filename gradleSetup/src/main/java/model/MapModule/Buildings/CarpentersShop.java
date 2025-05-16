package model.MapModule.Buildings;

import model.Enums.Buildings.BuildingType;
import model.Enums.Stores.CarpenterShopProducts;
import model.Enums.Stores.FishShopProducts;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Position;
import model.TimeSystem.DateTime;

import java.util.ArrayList;

public class CarpentersShop extends Store{
    private ArrayList<NpcProduct> dailyProductList;
    public CarpentersShop(Position startingPosition,boolean walkable, String name, Position doorPosition,  int height, int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
        dailyProductList = CarpenterShopProducts.getProducts(CarpenterShopProducts.class);
        setOpeningHour(9);
        setClosingHour(20);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        dailyProductList = CarpenterShopProducts.getProducts(CarpenterShopProducts.class);

    }

    public NpcProduct findBuildingByType(BuildingType buildingType) {
        for (NpcProduct product : dailyProductList) {
            if(product.getSaleable() instanceof BuildingType type){
                if(type.equals(buildingType)){
                    return product;
                }
            }
        }
        return null;
    }
}
