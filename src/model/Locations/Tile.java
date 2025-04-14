package model.Locations;

import model.GameObject.GameObject;
import model.GameObject.LivingEntity;

public class Tile {
    private Position position;
    private boolean isWalkable; // این به‌صورت دستی باید مقداردهی بشه یا از objectها گرفته بشه
    private GameObject fixedObject;
    private LivingEntity livingEntity;

    public Tile(Position position, GameObject fixedObject, LivingEntity livingEntity) {
        this.position = position;
        this.fixedObject = fixedObject;
        this.livingEntity = livingEntity;
        this.isWalkable = calculateWalkable();
    }

    private boolean calculateWalkable() {
//        if (livingEntity != null) return false;
//
//        if (fixedObject instanceof Movable mto) {
//            int dx = position.getX() - mto.getOriginTileX();
//            int dy = position.getY() - mto.getOriginTileY();
//            return mto.isTileWalkable(dx, dy);
//        }
//
//        if (fixedObject != null && !fixedObject.isWalkable()) return false;
//
//        // fallback در صورتی که نه موجودی هست، نه fixedObject
        return true;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public Position getPosition() {
        return position;
    }

    public GameObject getFixedObject() {
        return fixedObject;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public void setLivingEntity(LivingEntity entity) {
        this.livingEntity = entity;
        this.isWalkable = calculateWalkable(); // بروزرسانی وضعیت راه‌پذیری
    }

    public void setFixedObject(GameObject fixedObject) {
        this.fixedObject = fixedObject;
        this.isWalkable = calculateWalkable();
    }
}
