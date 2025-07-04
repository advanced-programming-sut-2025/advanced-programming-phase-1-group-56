package model.MapModule.Buildings;
import model.Enums.Animals.AnimalType;
import model.Enums.Stores.CarpenterShopProducts;
import model.Enums.Stores.FishShopProducts;
import model.Enums.Stores.MarniesRanchProducts;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Position;
import model.TimeSystem.DateTime;

import java.util.ArrayList;

public class MarniesRanch extends Store {
    public MarniesRanch( Position startingPosition,boolean walkable, String name, Position doorPosition, int height, int width) {
        super( startingPosition,walkable, name, doorPosition, height, width);
        dailyProductList = MarniesRanchProducts.getProducts(MarniesRanchProducts.class);
        setOpeningHour(9);
        setClosingHour(16);
    }

    @Override
    public void interact() {

    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(newDay){
            dailyProductList = MarniesRanchProducts.getProducts(MarniesRanchProducts.class);
        }
    }

    public NpcProduct findProductByAnimalType(AnimalType animalType) {
        for (NpcProduct product : dailyProductList) {
            if(product.getSaleable().equals(animalType)) {
                return product;
            }
        }
        return null;
    }
}
