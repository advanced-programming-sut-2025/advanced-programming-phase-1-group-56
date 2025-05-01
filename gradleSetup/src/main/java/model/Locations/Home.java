package model.Locations;

import model.GameObject.Refrigerator;
import model.items.Item;

public class Home extends Building {
    private Refrigerator myRefrigerator;

    public Home(String name, Position position, int width, int height, int insideWidth, int insideHeight, boolean[][] walkable, Refrigerator myRefrigerator) {
        super(name, position, width, height, insideWidth, insideHeight, walkable);
        this.myRefrigerator = myRefrigerator;
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
