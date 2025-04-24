package model.Locations;

public class Coop extends Building {
    private int capacity;
    private int price;

    public Coop(String name, Position position, int width, int height, int insideWidth, int insideHeight, boolean[][] walkable, int capacity, int price) {
        super(name, position, width, height, insideWidth, insideHeight, walkable);
        this.capacity = capacity;
        this.price = price;
    }

    @Override
    public void interact() {
        //TODO
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
