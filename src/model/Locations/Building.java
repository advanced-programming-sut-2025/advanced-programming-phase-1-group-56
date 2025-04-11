package model.Locations;

public class Building {
    private final int OutsideHeight;
    private final int OutsideWidth;
    private final int X;
    private final int Y;
    private final int InsideWIDTH;
    private final int InsideHEIGHT;

    Building(int x, int y, int outsideHeight, int outsideWidth, int insideWIDTH, int insideHEIGHT) {
        X = x;
        Y = y;
        this.OutsideHeight = outsideHeight;
        this.OutsideWidth = outsideWidth;
        this.InsideWIDTH = insideWIDTH;
        this.InsideHEIGHT = insideHEIGHT;
    }

    public int getOutsideHeight() {
        return OutsideHeight;
    }

    public int getOutsideWidth() {
        return OutsideWidth;
    }

    public int getInsideWIDTH() {
        return InsideWIDTH;
    }

    public int getInsideHEIGHT() {
        return InsideHEIGHT;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
