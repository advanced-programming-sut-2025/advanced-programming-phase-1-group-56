package model.Locations;

public abstract class Building {
    protected String name;
    protected final int width;
    protected final int height;
    protected final int posX;
    protected final int posY;
    protected final int InsideWidth;
    protected final int InsideHeight;

    public Building(String name, int width, int height, int posX, int posY,int insideWidth,int insideHeight) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.InsideWidth = insideWidth;
        this.InsideHeight = insideHeight;
    }

    public String getName() {
        return name;
    }

    public abstract void interact();
}
