package io.src.model.MapModule.Buildings;

import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.GameObject.Refrigerator;
import io.src.model.MapModule.Buildings.Building;
import io.src.model.MapModule.Position;

public class Home extends Building {
    private Refrigerator myRefrigerator;

    public Home(Position startingPosition,boolean walkable,String name,Position doorPosition,int height,int width) {
        super(startingPosition,walkable,name,doorPosition,height,width, BuildingType.HOME);
        this.myRefrigerator = null;
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
}
