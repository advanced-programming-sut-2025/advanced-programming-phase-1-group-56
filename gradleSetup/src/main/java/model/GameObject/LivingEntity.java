package model.GameObject;

import model.Enums.Direction;
import model.MapModule.Position;

public class LivingEntity extends GameObject implements Movable {
    public LivingEntity(Position position,boolean walkable) {
        super(walkable,position);
        this.position = position;
    }

    @Override
    public void move(Direction direction) {
        if (direction == Direction.UP) {
        } else if (direction == Direction.DOWN) {
        } else if (direction == Direction.LEFT) {
        } else if (direction == Direction.RIGHT) {
        } else if (direction == Direction.UPRIGHT) {
        } else if (direction == Direction.UPLEFT) {
        } else if (direction == Direction.DOWNRIGHT) {
        } else if (direction == Direction.DOWNLEFT) {
        }
        //TODO
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
