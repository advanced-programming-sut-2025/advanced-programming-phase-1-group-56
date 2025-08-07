package io.src.model.MapModule.Buildings;

import io.src.model.App;
import io.src.model.Enums.Stores.FishShopProducts;
import io.src.model.Enums.TileType;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Position;
import io.src.model.TimeSystem.DateTime;

import java.util.ArrayList;

import static io.src.model.MapModule.newFarmLoader.loadTheLocation;

public class FishShop extends Store {
    private ArrayList<NpcProduct> dailyProductList;
    public FishShop( Position startingPosition,boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
        dailyProductList = FishShopProducts.getProducts(FishShopProducts.class);
        setOpeningHour(9);
        setClosingHour(17);
        GameLocation indoor = loadTheLocation("assets\\gameLocations\\Fish_Shop_Indoor");
        setIndoor(indoor);
        setInitialPosition(new Position(5 , 2));
        indoor.getTiles()[1][5].setTileType(TileType.Wrapper);
        NPCposition = new Position(4, 5);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(newDay){
            dailyProductList = FishShopProducts.getProducts(FishShopProducts.class);
        }
    }

    @Override
    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }

    @Override
    public String getAssetName() {
        return "Fish_Shop_" + App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason().toString();
    }
}
