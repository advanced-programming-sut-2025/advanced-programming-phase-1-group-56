package io.src.model.MapModule.Buildings;

import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.GameObject.Refrigerator;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Position;

import static io.src.model.MapModule.newFarmLoader.loadTheLocation;

public class Home extends Building {
    private Refrigerator myRefrigerator;

    public Home(Position startingPosition,boolean walkable,String name,Position doorPosition,int height,int width) {
        super(startingPosition,walkable,name,doorPosition,height,width, BuildingType.HOME);
        this.myRefrigerator = null;
        GameLocation indoor = loadTheLocation("assets\\gameLocations\\Player_House_Indoor");
        setIndoor(indoor);
        setInitialPosition(new Position(4,4));
    }

    @Override
    public void interact() {

    }

    public Refrigerator getMyRefrigerator() {
        return myRefrigerator;
    }

    public void setMyRefrigerator(Refrigerator myRefrigerator) {
        this.myRefrigerator = myRefrigerator;
    }

    @Override
    public String getAssetName() {
        return "Croped_Player_House";
    }
}
