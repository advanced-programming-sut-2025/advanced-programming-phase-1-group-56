package io.src.model.MapModule.Buildings;

import io.src.model.App;
import io.src.model.Enums.Stores.FishShopProducts;
import io.src.model.Enums.Stores.PierreGeneralStoreProducts;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Position;
import io.src.model.TimeSystem.DateTime;

import java.util.ArrayList;

import static io.src.model.MapModule.newFarmLoader.loadTheLocation;

public class PierresGeneralStore extends Store {
    private ArrayList<NpcProduct> dailyProductList;

    public PierresGeneralStore(Position startingPosition, boolean walkable, String name, Position doorPosition, int height, int width) {
        super(startingPosition, walkable, name, doorPosition, height, width);
        dailyProductList = PierreGeneralStoreProducts.getProducts(PierreGeneralStoreProducts.class);
        setOpeningHour(9);
        setClosingHour(17);
        GameLocation indoor = loadTheLocation("assets\\gameLocations\\Pierres_General_Store_Indoor");
        setIndoor(indoor);
        setInitialPosition(new Position(5 , 5));
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(newDay){
            dailyProductList = PierreGeneralStoreProducts.getProducts(PierreGeneralStoreProducts.class);
        }
    }

    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }

    public void setDailyProductList(ArrayList<NpcProduct> dailyProductList) {
        this.dailyProductList = dailyProductList;
    }

    @Override
    public String getAssetName() {
        return "Pierre_General_Store_" + App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason().toString();
    }
}
