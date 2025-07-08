package io.src.model.MapModule;

public class Position {
    private float x;
    private float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void  setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void ChangePosition(float deltaX,float deltaY) {this.x += deltaX;this.y += deltaY;}

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
