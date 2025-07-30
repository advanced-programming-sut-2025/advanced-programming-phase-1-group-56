package io.src.model.GameObject;

import io.src.model.Enums.Direction;
import io.src.model.MapModule.Position;

public class LivingEntity extends GameObject implements Movable {
    public LivingEntity(Position position, boolean walkable) {
        super(walkable, position);
        this.position = position;
    }

    @Override
    public void move(Direction direction) {
//        if (direction == Direction.UP) {
//        } else if (direction == Direction.DOWN) {
//        } else if (direction == Direction.LEFT) {
//        } else if (direction == Direction.RIGHT) {
//        } else if (direction == Direction.UPRIGHT) {
//        } else if (direction == Direction.UPLEFT) {
//        } else if (direction == Direction.DOWNRIGHT) {
//        } else if (direction == Direction.DOWNLEFT) {
//        }
//        //TODO
    }

    @Override
    public String getAssetName() {
        //TODO
        return "";
    }
}
