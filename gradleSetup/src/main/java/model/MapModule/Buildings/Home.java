package model.MapModule.Buildings;

import model.GameObject.Refrigerator;
import model.MapModule.Buildings.Building;
import model.MapModule.Position;

public class Home extends Building {
    private Refrigerator myRefrigerator;

    public Home(Position startingPosition,boolean walkable,String name,Position doorPosition,int height,int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
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
