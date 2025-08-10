package io.src.model.MapModule.Buildings;

import io.src.model.App;
import io.src.model.Enums.GameLocationType;
import io.src.model.Enums.Stores.FishShopProducts;
import io.src.model.Enums.Stores.StardropSaloonProducts;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Position;
import io.src.model.TimeSystem.DateTime;
import static io.src.model.MapModule.newFarmLoader.loadTheLocation;


import java.util.ArrayList;

public class TheSaloonStardrop extends Store {
    private ArrayList<NpcProduct> dailyProductList;

    public TheSaloonStardrop(Position startingPosition, boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition, walkable, name, doorPosition, height, width);
        dailyProductList = StardropSaloonProducts.getProducts(StardropSaloonProducts.class);
        setOpeningHour(12);
        setClosingHour(24);
        GameLocation indoor = loadTheLocation("assets\\gameLocations\\The_Stardrop_Saloon_Indoor");
        setIndoor(indoor);
        setInitialPosition(new Position(14 ,2));
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            dailyProductList = StardropSaloonProducts.getProducts(StardropSaloonProducts.class);
        }
    }

    @Override
    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }


    @Override
    public String getAssetName() {
        return "The_Sallon_Stardrop_" + App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason().toString()
            + ((App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > 18) ? "_Night" : "");
    }
}
