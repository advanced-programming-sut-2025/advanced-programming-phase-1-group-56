package io.src.model.GameObject;

import com.badlogic.gdx.math.Vector2;
import io.src.model.Enums.Direction;
import io.src.model.MapModule.Position;

public class LivingEntity extends GameObject implements Movable {


    private Direction lastDirection = Direction.DOWN;
    private float speed = 6.25f;
    private float vx = 0, vy = 0;
    public LivingEntity(Position position, boolean walkable) {
        super(walkable, position);
        this.position = position;
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public Vector2 getVelocity() {
        return new Vector2(vx, vy);
    }

    public void update(float delta) {
        tryMove(vx * delta, vy * delta);
    }

    public boolean tryMove(float dx, float dy) {
        position.ChangePosition(dx, dy);
        return false;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setMovingDirection(Direction d) {
        if (d != null) {
            lastDirection = d;
        }
    }

    public boolean isMoving() {
        return vx != 0 || vy != 0;
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

    public float getSpeed() {
        return speed;
    }
}
