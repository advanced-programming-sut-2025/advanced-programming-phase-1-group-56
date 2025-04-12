package model.GameObject;

import model.Enums.Direction;
import model.GameMap.Position;

public class LivingEntity extends GameObject implements Movable {
    public LivingEntity(Position position, int width, int height, boolean[][] walkable) {
        super(position, width, height, walkable);
    }

    @Override
    public void move(Direction direction) {
        if (direction == Direction.UP) {}
        else if (direction == Direction.DOWN) {}
        else if (direction == Direction.LEFT) {}
        else if (direction == Direction.RIGHT) {}
        else if (direction == Direction.UPRIGHT) {}
        else if (direction == Direction.UPLEFT) {}
        else if (direction == Direction.DOWNRIGHT) {}
        else if (direction == Direction.DOWNLEFT) {}
        //TODO
    }

}
