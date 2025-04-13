package model.GameObject;

import model.locations.Position;
import model.items.Item;

import java.util.HashMap;

public class Refrigerator extends GameObject {
    private HashMap<Item,Integer> foods = new HashMap<>();

    public Refrigerator(Position position, int width, int height, boolean[][] walkable) {
        super(position, width, height, walkable);
    }

    public void addItems(Item food, int quantity) {
        //TODO
        foods.put(food, quantity);
    }
}
