package model.Locations;

public class GreenHouse extends Building {


    public GreenHouse(String name,int posX, int posY) {
        super(name, 6,8, posX, posY,5,6);
    }

    @Override
    public void interact() {

    }

    //TODO add tiles and field to this after adding items
}
