package io.src.model.MapModule.Buildings;

import io.src.model.App;
import io.src.model.Enums.Stores.BlackSmithProducts;
import io.src.model.Enums.Stores.FishShopProducts;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Position;
import io.src.model.TimeSystem.DateTime;

import java.util.ArrayList;

import static io.src.model.MapModule.newFarmLoader.loadTheLocation;


public class Blacksmith extends Store {
    private ArrayList<NpcProduct> dailyProductList;

    public Blacksmith(Position startingPosition, boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition, walkable, name, doorPosition, height, width);
        dailyProductList = BlackSmithProducts.getProducts(BlackSmithProducts.class);
        setOpeningHour(9);
        setClosingHour(16);
        GameLocation indoor = loadTheLocation("assets\\gameLocations\\Blacksmith_Indoor");
        setIndoor(indoor);
        setInitialPosition(new Position(5 , 2));
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            dailyProductList.clear();
            dailyProductList = FishShopProducts.getProducts(BlackSmithProducts.class);
        }
    }

    @Override
    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }

    @Override
    public String getAssetName() {
        return "Blacksmith_" + App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason().toString();
    }
}
