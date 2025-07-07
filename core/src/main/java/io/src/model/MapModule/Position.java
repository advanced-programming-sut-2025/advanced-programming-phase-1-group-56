package io.src.model.MapModule;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void  setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void ChangePosition(int deltaX,int deltaY) {this.x += deltaX;this.y += deltaY;}

    public boolean isNear(Position pos2,int range) {
        return Math.abs(pos2.getX() - this.x) <= range && Math.abs(pos2.getY() - this.y) <= range;
    }

    @Override
    public boolean equals(Object pos2) {
        if(((Position)pos2).getX() == this.x && ((Position)pos2).getY() == this.y){
            return true;
        }
        return false;
    }

}
