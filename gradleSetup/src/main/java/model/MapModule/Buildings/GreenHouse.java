package model.MapModule.Buildings;

import model.MapModule.Position;

public class GreenHouse extends Building {


    public GreenHouse(Position startingPosition,boolean walkable,String name,Position doorPosition,int height,int width) {
        super(startingPosition,walkable,name,doorPosition,height,width);
    }

    @Override
    public void interact() {

    }

    //TODO add tiles and field to this after adding items
}
