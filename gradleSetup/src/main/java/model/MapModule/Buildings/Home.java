package model.MapModule.Buildings;

import model.GameObject.Refrigerator;
import model.MapModule.Buildings.Building;
import model.MapModule.Position;

public class Home extends Building {
    private Refrigerator myRefrigerator;

    public Home(boolean walkable,String name,Position doorPosition,Position startingPosition,int height,int width) {
        super(walkable,name,doorPosition,startingPosition,height,width);
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
