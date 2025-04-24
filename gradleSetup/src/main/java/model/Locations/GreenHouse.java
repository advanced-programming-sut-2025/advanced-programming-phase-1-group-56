package model.Locations;

public class GreenHouse extends Building {


    public GreenHouse(String name,int posX, int posY) {
        super(name, new Position(posX, posY),6, 8, 6,5,new boolean[6][8]);
    }

    @Override
    public void interact() {

    }

    //TODO add tiles and field to this after adding items
}
