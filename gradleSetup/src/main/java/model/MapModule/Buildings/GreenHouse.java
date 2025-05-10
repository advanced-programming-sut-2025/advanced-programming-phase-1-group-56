package model.MapModule.Buildings;

import model.MapModule.Position;

public class GreenHouse extends Building {


    public GreenHouse(boolean walkable,String name,Position doorPosition,Position startingPosition,int height,int width) {
        super(walkable,name,doorPosition,startingPosition,height,width);
    }

    @Override
    public void interact() {

    }

    //TODO add tiles and field to this after adding items
}
