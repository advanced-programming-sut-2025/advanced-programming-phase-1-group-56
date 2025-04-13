package model.Locations;

public class GreenHouse extends Building {


    public GreenHouse(String name,Position position) {
        super(name,  position,8, 7, 5,4,new boolean[5][4]);
    }

    @Override
    public void interact() {

    }

    //TODO add tiles and field to this after adding items
}
